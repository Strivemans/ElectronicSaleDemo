package com.jack.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.demo.common.R;
import com.jack.demo.entity.*;
import com.jack.demo.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * \* User: jack
 * \* Date: 2022/10/18
 * \* Time: 8:27
 * \* Description: 订单控制类
 */
@RestController
@RequestMapping("/order")
@CrossOrigin
@Api("订单管理")
public class OrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private ClientService clientService;
    @Resource
    private GoodService goodService;
    @Resource
    private GoodOrderService goodOrderService;
    @Resource
    private SalerOrderService salerOrderService;
    @Resource
    private SalerService salerService;
    @Resource
    private SalerProfitService salerProfitService;
    @Resource
    private AfterSalesService afterSalesService;

    @GetMapping("countOrderProfit")
    @ApiOperation("获取订单的成本和利润")
    public R getOrderProfit(@ApiParam(value = "orderId",name = "订单id",required = false) @RequestParam(required = false) Integer orderId){
        List<Order> orders = orderService.countOrderList(orderId);
        return R.ok().message("查询成功").data("list",orders);
    }

    @RequestMapping("make")
    @ApiOperation("下订单")
    public R makeOrder(@ApiParam(name = "make",value = "下订单类",required = true) @RequestBody Make make){
        //1. 确定该顾客 是不是 老顾客 不是则创建顾客
        Client client = isOldClient(make);
        //2. 添加订单信息
        //2.1 拿到订单的数额
        double price = countOrderPrice(make, client);
        BigDecimal bigDecimal = new BigDecimal(price);
        price = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        //2.2 数据库创建订单信息
        Date date = new Date(System.currentTimeMillis());

        orderService.save(new Order(price, client.getClientId(),make.getSalerName(),date));

        //3. 建立 订单与产品关联信息
        //3.1 拿到新建的订单信息 id
        Order newOrder = orderService.getOrderByClientAndPrice(client.getClientId(), price);
        //3.2 添加 订单与产品关联信息
        makeGoodOrderRel(make,newOrder);

        //4. 让相关的产品的库存减少 和 增加产品的销售量
        HashMap<Integer, Integer> goodStockMap = new HashMap<>();
        for(Good good:make.getGoodList()){
            goodStockMap.put(good.getGoodId(),good.getNum());
        }
        goodService.updateGoodStockAndSales(goodStockMap);


        //5. 修改 销售员 与 订单 的关联信息
        salerOrderService.save(new SalerOrder(salerService.getSalerByName(make.getSalerName()).getSalerId(),newOrder.getOrderId()));

        //6. 添加 销售员 与 利润表 的信息
        double profit = countSalerProfit(make);
        SalerProfit salerProfit = new SalerProfit(salerService.getSalerByName(make.getSalerName()).getSalerId(),newOrder.getOrderId(),profit);
        salerProfitService.save(salerProfit);

        //7. 返回本次订单的信息
        newOrder.setGoodList(make.getGoodList());
        return R.ok().message("下单成功").data("order",newOrder);
    }

    @PostMapping("update")
    @ApiOperation("修改订单")
    public R update(@ApiParam(name = "order",value = "订单类",required = true) @RequestBody Order order){
        //1. 获取客户名拿到要修改的id
        Client client = clientService.getClientByName(order.getClientName());
        order.setClientId(client.getClientId());

        //2. 调整产品的库存 和 销售量
        List<GoodOrder> goodOrderList = goodOrderService.getGoodOrderByOrderId(order.getOrderId());
        adjustGoodStockAndSales(order,goodOrderList);

        //3. 更新销售员的利润提成
        //3.1 判断 销售员是否发生改变
        Saler saler = salerService.getSalerByName(order.getSalerName());
        SalerProfit salerProfit = salerProfitService.getByOrderId(order.getOrderId());
        //3.2 不管相不相等 都需要把原来的销售员利润给删除 然后再给新的销售员添加利润
        // 销售员订单关联信息 相等：不需要修改 不相等：需要修改
        if(saler.getSalerId() != salerProfit.getSalerId()){
            //销售员发生改变
            //删除原来的订单销售员关联表
            salerOrderService.delSalerOrder(order.getOrderId());
            //给新的销售员 添加 订单关联信息
            salerOrderService.save(new SalerOrder(saler.getSalerId(),order.getOrderId()));
        }
        //删除利润
        salerProfitService.delByOrderId(salerProfit.getOrderId());

        //3.3 给销售员添加利润信息
        double profit = countSalerProfit(new Make(order.getGoodList())); //计算利润
        salerProfitService.save(new SalerProfit(saler.getSalerId(),order.getOrderId(),profit)); //新增新的利润信息

        //4. 调整产品与订单联系
        //4.1 先删除之前的联系
        goodOrderService.deleteGoodOrder(order.getOrderId());
        //4.2 建立新的联系
        makeGoodOrderRel(new Make(order.getGoodList()),order);

        //5.计算订单的金额
        double pricer = countOrderPrice(new Make(order.getGoodList()), client);
        order.setOrderMoney(pricer);

        //6. 更新订单类的信息
        orderService.updateOrder(order);

        return R.ok().message("修改成功");
    }

    @GetMapping("delete")
    @ApiOperation("删除订单")
    public R delete(@ApiParam(name = "orderId",value = "订单id",required = true) @RequestParam Integer orderId){
        //1. 删除 订单产品关联信息
        goodOrderService.deleteGoodOrder(orderId);
        //2. 删除 销售员与订单关联信息
        salerOrderService.delSalerOrder(orderId);
        //3. 删除对应的 售后信息
        afterSalesService.delAfterByOrderId(orderId);
        //4. 删除订单信息
        orderService.delOrder(orderId);
        return R.ok().message("删除成功");
    }

    @GetMapping("getAll")
    @ApiOperation("查询所有订单")
    public R getAll(@ApiParam(name = "orderId",value = "订单id",required = false) @RequestParam(required = false) Integer orderId,
                    @ApiParam(name = "orderState",value = "订单状态",required = false) @RequestParam(required = false) String orderState){
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        List<Order> orderList = null;
        if(orderId!=null && orderState!=null){
            orderQueryWrapper.eq("order_id",orderId);
            orderQueryWrapper.eq("order_state",orderState);
            orderList = orderService.list(orderQueryWrapper);
        }else if(orderId!=null && orderState==null){
            orderQueryWrapper.eq("order_id",orderId);
            orderList = orderService.list(orderQueryWrapper);
        }else if(orderId==null && orderState!=null){
            orderQueryWrapper.eq("order_state",orderState);
            orderList = orderService.list(orderQueryWrapper);
        }else{
            orderList = orderService.list(orderQueryWrapper);
        }
        for(Order order:orderList){
            order.setClientName(clientService.getClientById(order.getClientId()).getClientName());
            order.setGoodOrderList(goodOrderService.findGoodOrderListByOrderId(order.getOrderId()));
        }
        return R.ok().message("查询成功").data("list",orderList);
    }

    @GetMapping("getById")
    @ApiOperation("根据订单id获取订单信息")
    public R getyId(@ApiParam(name = "orderId",value = "订单id",required = true) @RequestParam Integer orderId){
        Order order = orderService.getOrderById(orderId);
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        return R.ok().message("查询成功").data("order",order).data("list",orderList);
    }

    @GetMapping("countNumByDate")
    @ApiOperation("根据日期统计时间")
    public R countNumByDate(){
        List<CountOrderNum> countOrderNums = orderService.CountOrderNumByDate();
        return R.ok().message("查询成功").data("list",countOrderNums);
    }

    /**
     * 调整产品的 库存 和 销售量
     * @param order
     * @param goodOrderList
     */
    public void adjustGoodStockAndSales(Order order,List<GoodOrder> goodOrderList){
        boolean isExists = false; //判断 新修改的产品是不是新产品
        for(int i=0;i<order.getGoodList().size();i++){
            //逐一比对当前修改的产品 是否还有在产品联系表中 进行对应的处理
            Good newGood = order.getGoodList().get(i); //获取每一个修改的产品
            Good goodObj = goodService.getGoodByName(newGood.getGoodName()); //获取 该产品的完整信息
            // 设置该产品的 库存和销售量
            newGood.setGoodStock(goodObj.getGoodStock());
            newGood.setGoodInventory(goodObj.getGoodInventory());

            for(GoodOrder goodOrder : goodOrderList){
                isExists = updateGoodSalesAndStock(newGood,goodOrder);
                if(isExists){
                    break; //退出本次循环
                }
            }
            //新添加的产品
            if(!isExists){
                //纯修改自己的库存和销售量 与 订单产品无关
                //减少库存 增加销售量
                Integer num = newGood.getNum();
                newGood.setGoodStock(newGood.getGoodStock() - num);
                newGood.setGoodInventory(newGood.getGoodInventory() + num);
                goodService.updateGood(newGood);
            }
        }
    }

    /**
     * 在产品id一样的情况下 更新产品的库存和销售量
     * @param newGood : 新添加的产品
     * @param goodOrder ： 产品订单类
     * @return : 是否修改成功（如果不是用一个goodid则为false）
     */
    public boolean updateGoodSalesAndStock(Good newGood,GoodOrder goodOrder){
        Integer sales = newGood.getGoodInventory(); // 获取原来的销售量
        Integer spread = 0; //差额
        Integer stock = newGood.getGoodStock(); // 拿到原本的库存

        //判断 是不是 本来就购买的产品
        //是： 再判断一下 是买多 还是 买少
        if(newGood.getGoodId() == goodOrder.getGoodId() && maxNum(newGood.getNum(),goodOrder.getNum())) {
            //还在 做两件事： 修改销售量 修改库存
            //销售量需要添加 库存需要减少(买多了)
            spread = newGood.getNum() - goodOrder.getNum(); //计算差额
            // 判断是不是本来库存就为0
            // 是的话 则 只修改销售量 不修改库存
            if(newGood.getGoodStock() == 0){
                sales += spread;
            }else {
                sales += spread;
                stock -= spread;
            }
            newGood.setGoodInventory(sales);
            newGood.setGoodStock(stock);
            goodService.updateGood(newGood);
            return true;
        }else{
            //需要先判断 销售量是否一致
            if (newGood.getNum() != goodOrder.getNum() && newGood.getGoodId() == goodOrder.getGoodId()) {
                spread = goodOrder.getNum() - newGood.getNum();
                //不一致 销售量需要减少 库存需要增加(即 买少了)
                // 判断是不是本来库存就为0
                // 是的话 则 只修改销售量 不修改库存
                if(newGood.getGoodStock() == 0){
                    sales -= spread;
                }else{
                    sales -= spread;
                    stock += spread;
                }
                // 最后更新 销售量
                newGood.setGoodInventory(sales);
                newGood.setGoodStock(stock);
                goodService.updateGood(newGood);
                //修改数据库
                return true;
            }else{
                return newGood.getGoodId() == goodOrder.getGoodId();
            }
        }
    }


    /**
     * 两值比较 获取较大的值
     * @param num1 ：数值1
     * @param num2 ：数值2
     * @return ：true 数值1大 反之亦然
     */
    public Boolean maxNum(Integer num1,Integer num2){
        return num1>num2?true:false;
    }

    /**
     * 计算销售员这一单的利润
     * @param make : 前端传来的数据
     * @return ： 返回 计算出来的利润
     */
    public double countSalerProfit(Make make){
        double profit = 0;
        for(Good good:make.getGoodList()){
            Double goodPrice = good.getGoodPrice();
            if(goodPrice<1000){
                profit += goodPrice * 0.1;
            }else if(goodPrice>1000 && goodPrice<2000){
                profit += goodPrice * 0.12;
            }else{
                profit += goodPrice * 0.08;
            }
        }
        return  profit;
    }

    /**
     * 构建订单与产品之间的关联
     * @param make : 前端传来的数据
     * @param newOrder ： 新创建的订单
     */
    public void makeGoodOrderRel(Make make,Order newOrder){
        List<HashMap<String, Integer>> goodAndNum = new ArrayList<>();  //产品id与数量
        List<HashMap<Integer,Double>> goodAndPrice = new ArrayList<>(); //产品的价格
        for(int i=0;i<make.getGoodList().size();i++){
            Good good = make.getGoodList().get(i); //拿到每一个good对象
            //让good拥有good_id字段
            Good goodData = goodService.getGoodByName(good.getGoodName());
            good.setGoodId(goodData.getGoodId());

            //建立产品id 与 数量的 list map集合
            HashMap<String, Integer> tempHash = new HashMap<>();
            tempHash.put("good_id",good.getGoodId());
            tempHash.put("num",good.getNum());
            goodAndNum.add(tempHash);

            //建立 产品id 与 购买价格（根据产品价格 * 数量）的 list map集合
            HashMap<Integer, Double> tempHashByPrice = new HashMap<>();
            Double totalPrice = good.getGoodPrice() * good.getNum();
            tempHashByPrice.put(good.getGoodId(),totalPrice);
            goodAndPrice.add(tempHashByPrice);
        }
        goodOrderService.OrderRelGood(newOrder.getOrderId(),goodAndNum,goodAndPrice);
    }

    /**
     * 计算订单的金额
     * @param make： 前端返回类型
     * @param client ： 购买的客户
     * @return 返回订单金额
     */
    public double countOrderPrice(Make make, Client client){
        double price = 0;
        for(Good good:make.getGoodList()){
            //不同的 会员等级 享有不同的优惠
            if(client.getClientRank() == 2){
                //黄金会员 每件产品优惠 九折
                price += good.getGoodPrice()*good.getNum()*0.09;
            }else if(client.getClientRank() == 3){
                //铂金会员 每件产品优惠 8.5折
                price += good.getGoodPrice()*good.getNum()*0.85;
            }else{
                //普通会员 没有优惠 原价
                price += good.getGoodPrice()*good.getNum();
            }
        }
        return price;
    }

    /**
     * 根据客户名 判断 用户 是不是老用户
     * @param make ： 前端的返回类型
     * @return : 返回一个客户信息
     */
    public Client isOldClient(Make make){
        Client client = clientService.getClientByName(make.getClientName());
        if(client == null){
            //新顾客  创建顾客 并 获取到 新创建顾客的id
            clientService.save(new Client(make.getClientName(),make.getClientTel(),make.getClientRank(),make.getClientAddress()));
            client = clientService.getClientByName(make.getClientName());
        }
        return client;
    }
}
