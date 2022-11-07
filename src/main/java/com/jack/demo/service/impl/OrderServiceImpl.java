package com.jack.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.demo.entity.*;
import com.jack.demo.mapper.OrderMapper;
import com.jack.demo.service.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * \* User: jack
 * \* Date: 2022/10/15
 * \* Time: 14:56
 * \* Description: 订单类实现层
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Resource
    private SalerService salerService;
    @Resource
    private GoodOrderService goodOrderService;
    @Resource
    private GoodService goodService;
    @Resource
    private ClientService clientService;
    /**
     * 根据客户id 与 价格 查询id
     * @param clientId 客户id
     * @param price 客户价格
     * @return 订单信息
     */
    @Override
    public Order getOrderByClientAndPrice(Integer clientId, Double price) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("client_id",clientId);
        orderQueryWrapper.eq("order_money",price);
        Order order = baseMapper.selectOne(orderQueryWrapper);
        return order;
    }

    /**
     * 删除订单
     * @param orderId 订单id
     */
    @Override
    public void delOrder(Integer orderId) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_id",orderId);
        baseMapper.delete(orderQueryWrapper);
    }

    /**
     * 修改订单信息
     * @param order : 需要修改的订单信息
     */
    @Override
    public void updateOrder(Order order) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_id",order.getOrderId());
        baseMapper.update(order,orderQueryWrapper);
    }

    /**
     * 根据订单id 获取 订单信息
     * @param orderId ： 订单id
     * @return： 返回订单信息
     */
    @Override
    public Order getOrderById(Integer orderId) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_id",orderId);
        Order order = baseMapper.selectOne(orderQueryWrapper);
        Saler saler = salerService.getSalerByName(order.getSalerName());
        order.setSalerId(saler.getSalerId());
        Client client = clientService.getClientById(order.getClientId());
        order.setClientName(client.getClientName());
        return order;
    }

    /**
     * 输入年份 获得对应的 年销售额
     * @param year: 年份
     * @return ： 年销售额
     */
    @Override
    public Double getTotalSaleByYear(String year) {
        Double totalSales = 0.0;
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.like("order_date","%"+year+"%");
        List<Order> orders = baseMapper.selectList(orderQueryWrapper);
        for(Order order:orders){
            totalSales+=order.getOrderMoney();
        }
        return totalSales;
    }

    /**
     * 获取 一年的已支付的订单数
     * @param year
     * @return
     */
    @Override
    public Integer getOrderByPay(String year) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.ne("order_state","未支付");
        orderQueryWrapper.like("order_date","%"+year+"%");
        orderQueryWrapper.select("count(*)");
        Integer orderNumByPay = baseMapper.selectCount(orderQueryWrapper);
        return orderNumByPay;
    }

    /**
     * 通过日期 获取 今天的订单数
     * @param date
     * @return
     */
    @Override
    public Integer getOrderNumByDate(String date) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.like("order_date",date);
        orderQueryWrapper.ne("order_state","未支付");
        orderQueryWrapper.select("count(*)");
        Integer todayOrder = baseMapper.selectCount(orderQueryWrapper);
        return todayOrder;
    }

    /**
     * 根据日期统计订单数
     * @return
     */
    @Override
    public List<CountOrderNum> CountOrderNumByDate() {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.select("distinct order_date");
        orderQueryWrapper.ne("order_state","未支付");
        List<Order> dateList = baseMapper.selectList(orderQueryWrapper);
        List<Order> orderList = baseMapper.selectList(null);
        List<CountOrderNum> countOrderNums = new ArrayList<>();
        for(Order date:dateList){
            CountOrderNum countOrderNum = new CountOrderNum();
            Integer num = 0;
            DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateFormet = simpleDateFormat.format(date.getOrderDate());
            boolean isEsits = false;
            for(CountOrderNum temp:countOrderNums){
                String format = simpleDateFormat.format(temp.getDate());
                if(format.equals(dateFormet)){
                    isEsits = true;
                }
            }
            if(!isEsits){
                countOrderNum.setDate(date.getOrderDate());
            }else{
                continue;
            }
            for(Order order:orderList){
                String format = simpleDateFormat.format(order.getOrderDate());
                if(format.equals(dateFormet)){
                    num++;
                }
            }
            countOrderNum.setNum(num);
            countOrderNums.add(countOrderNum);
        }
        return countOrderNums;
    }

    /**
     * 获取订单列表的成本 和 利润
     * @param orderId
     * @return
     */
    @Override
    public List<Order> countOrderList(Integer orderId) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        if(orderId!=null){
            orderQueryWrapper.eq("order_id",orderId);
        }
        // 获取订单列表
        List<Order> orders = baseMapper.selectList(orderQueryWrapper);
        // 获取订单产品关联列表 获取每个产品的利润 以此 统计 该订单的利润值
        for(Order order : orders){
            List<GoodOrder> goodOrderList = goodOrderService.getGoodOrderByOrderId(order.getOrderId());
            Double totalProfit = 0.0;
            for(GoodOrder goodOrder:goodOrderList){
                Good good = goodService.getGoodById(goodOrder.getGoodId());
                Double profit = (good.getGoodPrice() / good.getGoodProfit()) * goodOrder.getNum();
                totalProfit += profit;
            }
            BigDecimal bigDecimal = new BigDecimal(totalProfit).setScale(2, RoundingMode.HALF_UP);
            order.setProfit(bigDecimal.doubleValue());
        }
        return orders;
    }
}
