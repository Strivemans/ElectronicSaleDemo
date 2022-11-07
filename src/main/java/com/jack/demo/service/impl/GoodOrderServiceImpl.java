package com.jack.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.demo.entity.Good;
import com.jack.demo.entity.GoodOrder;
import com.jack.demo.entity.Saler;
import com.jack.demo.mapper.GoodOrderMapper;
import com.jack.demo.mapper.SalerMapper;
import com.jack.demo.service.GoodOrderService;
import com.jack.demo.service.GoodService;
import com.jack.demo.service.SalerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * \* User: jack
 * \* Date: 2022/10/15
 * \* Time: 14:56
 * \* Description: 产品订单关联类实现层
 */
@Service
public class GoodOrderServiceImpl extends ServiceImpl<GoodOrderMapper, GoodOrder> implements GoodOrderService {
    @Resource
    private GoodService goodService;

    /**
     * 修改产品订单关联表
     * @param goodOrder
     */
    @Override
    public void updateGoodOrder(GoodOrder goodOrder) {
        QueryWrapper<GoodOrder> goodOrderQueryWrapper = new QueryWrapper<>();
        goodOrderQueryWrapper.eq("go_id",goodOrder.getGoId());
        baseMapper.update(goodOrder,goodOrderQueryWrapper);
    }

    /**
     * 根据订单id 判断 订单中购买的产品 库存是否满足需求
     * @param orderId : 订单id
     * @return： true： 库存不足 false： 库存充足
     */
    @Override
    public Boolean isStockOutByOrderId(Integer orderId) {
        List<GoodOrder> GoodOrderList = findGoodOrderListByOrderId(orderId);
        for(GoodOrder goodOrder:GoodOrderList){
            if(goodOrder.getStockOut() > 0){
                return true;
            }
        }
        return false;
    }

    /**
     * 订单表 与 产品表进行 关联
     * @param orderId : 订单id
     * @param goodAndNum : 存储产品信息
     * @param goodAndPrice : 存储价格
     */
    @Override
    public void OrderRelGood(Integer orderId, List<HashMap<String, Integer>> goodAndNum, List<HashMap<Integer, Double>> goodAndPrice) {
        for(int i=0;i<goodAndNum.size();i++){
            Integer stockOut = 0;
            // 获取产品id
            Integer goodId = goodAndNum.get(i).get("good_id");
            // 获取产品购买的数量
            Integer num = goodAndNum.get(i).get("num");
            // 判断产品的库存是否充足 不充足 则添加 缺货字段
            Good good = goodService.getGoodById(goodId);
            if(good.getGoodStock() < num){
                stockOut = num - good.getGoodStock();
            }
            // 获取价格
            Double price = goodAndPrice.get(i).get(goodId);
            baseMapper.insert(new GoodOrder(goodId,orderId,num,stockOut,price));
        }
    }

    /**
     * 根据订单id
     * @param orderId：订单id
     */
    @Override
    public void deleteGoodOrder(Integer orderId) {
        QueryWrapper<GoodOrder> goodOrderQueryWrapper = new QueryWrapper<>();
        goodOrderQueryWrapper.eq("order_id",orderId);
        baseMapper.delete(goodOrderQueryWrapper);
    }

    /**
     * 根据订单id 查询 订单产品关联信息
     * @param orderId ： 订单id
     * @return : 返回订单信息集合
     */
    @Override
    public List<GoodOrder> findGoodOrderListByOrderId(Integer orderId) {
        QueryWrapper<GoodOrder> goodOrderQueryWrapper = new QueryWrapper<>();
        goodOrderQueryWrapper.eq("order_id",orderId);
        List<GoodOrder> goodOrders = baseMapper.selectList(goodOrderQueryWrapper);
        return goodOrders;
    }

    /**
     * 根据订单id 获取 对应的 产品信息
     * @param orderId : 订单id
     * @return： 产品列表
     */
    @Override
    public List<Good> getGoodListByOrderId(Integer orderId) {
        List<Good> goodList = new ArrayList<>();
        //1. 查询 产品与对应订单关联数据
        List<GoodOrder> goodOrderList = getGoodOrderByOrderId(orderId);

        //2. 通过goodOrders中的 产品id 获取对应的产品信息
        Integer keyNum = 1; //用于在前端 编辑产品信息时 所使用的 索引
        for(GoodOrder goodOrder:goodOrderList){
            Good good = goodService.getGoodById(goodOrder.getGoodId());
            good.setNum(goodOrder.getNum());
            good.setEditable(false);
            good.setKey(keyNum++);
            // 处理库存不足情况
            if(goodOrder.getStockOut() > 0 && good.getGoodStock() > 0 ){ //库存更新 重新计算是否缺货
                Integer num = good.getGoodStock() - goodOrder.getStockOut();
                if(num < 0) { // 添加的库存 还不足以 弥补 缺货数
                    // 添加缺货信息
                    good.setStockOutInfo("缺货："+num);
                    // 更新库存 和 缺货值
                    good.setGoodStock(0);
                    goodOrder.setStockOut(num);
                    good.setStockOutInfo("缺货："+num);
                }else{
                    good.setGoodStock(num);
                    goodOrder.setStockOut(0);
                }
                goodService.updateGood(good);
                updateGoodOrder(goodOrder);
            }else if(goodOrder.getStockOut() > 0 && good.getGoodStock() == 0 ){  //库存没有更新
                good.setStockOutInfo("缺货："+goodOrder.getStockOut());
            }
            goodList.add(good);
        }
        return goodList;
    }

    /**
     * 根据订单id 获取 对应的产品订单联系表
     * @param orderId ： 订单id
     * @return : 返回对应的订单产品联系数据
     */
    @Override
    public List<GoodOrder> getGoodOrderByOrderId(Integer orderId) {
        QueryWrapper<GoodOrder> goodOrderQueryWrapper = new QueryWrapper<>();
        goodOrderQueryWrapper.eq("order_id",orderId);
        List<GoodOrder> goodOrders = baseMapper.selectList(goodOrderQueryWrapper);
        return goodOrders;
    }
}
