package com.jack.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.demo.entity.*;
import com.jack.demo.mapper.AccountMapper;
import com.jack.demo.mapper.AfterSalesMapper;
import com.jack.demo.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * \* User: jack
 * \* Date: 2022/10/15
 * \* Time: 14:56
 * \* Description: 售后类实现层
 */
@Service
public class AfterSalesServiceImpl extends ServiceImpl<AfterSalesMapper, AfterSales> implements AfterSalesService {
    @Resource
    private ClientService clientService;
    @Resource
    private SalerService salerService;
    @Resource
    private GoodOrderService goodOrderService;
    @Resource
    private OrderService orderService;
    /**
     * 获取 售后信息列表
     * @return 返回售后信息列表
     */
    @Override
    public List<AfterSales> getAfterList(Integer orderId,String state) {
        QueryWrapper<AfterSales> afterWrapper = new QueryWrapper<>();
        if(orderId!=null && state!=null){
            afterWrapper.eq("order_id",orderId);
            afterWrapper.eq("after_state",state);
        }else if(orderId!=null && state==null){
            afterWrapper.eq("order_id",orderId);
        }else if(orderId==null && state!=null){
            afterWrapper.eq("after_state",state);
        }
        List<AfterSales> afterList = baseMapper.selectList(afterWrapper);
        for(AfterSales after:afterList){
            after.setClientName(clientService.getClientById(after.getClientId()).getClientName());
            after.setSalerName(salerService.getSalerById(after.getSalerId()).getSalerName());
        }
        return afterList;
    }


    /**
     * 获取售后的详情信息
     * @param afterId ： 售后id
     * @return ： 返回售后详情类
     */
    @Override
    public AfterDetail getAfterDetail(Integer afterId) {
        AfterDetail afterDetail = new AfterDetail();
        //1. 获取售后表单信息
        QueryWrapper<AfterSales> afterSalesQueryWrapper = new QueryWrapper<>();
        afterSalesQueryWrapper.eq("after_id",afterId);
        AfterSales after = baseMapper.selectOne(afterSalesQueryWrapper);
        Order order = orderService.getOrderById(after.getOrderId());
        afterDetail.setOrderSalerName(order.getSalerName());
        afterDetail.setAfterSales(after);

        //2. 获取客户信息
        Client client = clientService.getClientById(after.getClientId());
        if(client.getClientRank() == 1){
            client.setRankName("普通会员");
        }else if(client.getClientRank() == 2){
            client.setRankName("黄金会员");
        }else {
            client.setRankName("铂金会员");
        }
        afterDetail.setClient(client);

        //3. 获取该订单购买的产品相关信息
        List<Good> goodList = goodOrderService.getGoodListByOrderId(after.getOrderId());
        afterDetail.setGoodList(goodList);
        List<GoodOrder> goodOrderList = goodOrderService.getGoodOrderByOrderId(after.getOrderId());
        for(GoodOrder goodOrder:goodOrderList){
           for(Good good:goodList){
               if(good.getGoodId() == goodOrder.getGoodId()){
                   good.setNum(goodOrder.getNum());
                   good.setTotalPrice(goodOrder.getPrice());
               }
           }
        }

        return afterDetail;
    }

    /**
     * 根据订单id删除售后信息
     * @param orderId ： 订单id
     */
    @Override
    public void delAfterByOrderId(Integer orderId) {
        QueryWrapper<AfterSales> afterSalesQueryWrapper = new QueryWrapper<>();
        afterSalesQueryWrapper.eq("order_id",orderId);
        baseMapper.delete(afterSalesQueryWrapper);
    }

    /**
     * 根据订单id 查询 售后信息
     * @param orderId : 订单id
     * @return ： 返回售后基本信息
     */
    @Override
    public AfterSales getAfterByOrderId(Integer orderId) {
        QueryWrapper<AfterSales> afterSalesQueryWrapper = new QueryWrapper<>();
        afterSalesQueryWrapper.eq("order_id",orderId);
        AfterSales afterSales = baseMapper.selectOne(afterSalesQueryWrapper);
        return afterSales;
    }
}
