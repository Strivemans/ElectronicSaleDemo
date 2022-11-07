package com.jack.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.demo.entity.*;
import com.jack.demo.mapper.CategoryMapper;
import com.jack.demo.mapper.SalerOrderMapper;
import com.jack.demo.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * \* User: jack
 * \* Date: 2022/10/15
 * \* Time: 14:56
 * \* Description: 销售员与订单关联实现层
 */
@Service
public class SalerOrderServiceImpl extends ServiceImpl<SalerOrderMapper, SalerOrder> implements SalerOrderService {
    @Resource
    private SalerService salerService;
    @Resource
    private SalerProfitService salerProfitService;
    @Resource
    private OrderService orderService;
    @Override
    public void delSalerOrder(Integer orderId) {
        QueryWrapper<SalerOrder> salerOrderQueryWrapper = new QueryWrapper<>();
        salerOrderQueryWrapper.eq("order_id",orderId);
        baseMapper.delete(salerOrderQueryWrapper);
    }

    @Override
    public List<SalerOrder> getSakerProfit(String salerName) {
        QueryWrapper<SalerOrder> salerOrderQueryWrapper = new QueryWrapper<>();
        Saler saler = null;
        if(salerName!=null){
            saler = salerService.getSalerByName(salerName);
            salerOrderQueryWrapper.eq("saler_id",saler.getSalerId());
        }
        List<SalerOrder> salerOrders = baseMapper.selectList(salerOrderQueryWrapper);
        for(SalerOrder salerOrder: salerOrders){
            SalerProfit salerProfit = salerProfitService.getByOrderId(salerOrder.getOrderId());
            salerOrder.setProfit(salerProfit.getProfit());
            Order order = orderService.getOrderById(salerOrder.getOrderId());
            salerOrder.setCreateTime(order.getOrderDate());
            if(saler == null){  //没有条件
                Saler tempSale = salerService.getSalerById(salerOrder.getSalerId());
                salerOrder.setSalerName(tempSale.getSalerName());
            }else {
                salerOrder.setSalerName(saler.getSalerName());
            }
        }
        return salerOrders;
    }
}
