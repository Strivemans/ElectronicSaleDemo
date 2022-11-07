package com.jack.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.demo.entity.Account;
import com.jack.demo.entity.CountOrderNum;
import com.jack.demo.entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderService extends IService<Order> {
    Order getOrderByClientAndPrice(Integer clientId,Double price);

    void delOrder(Integer orderId);

    void updateOrder(Order order);

    Order getOrderById(Integer orderId);

    Double getTotalSaleByYear(String year);

    Integer getOrderByPay(String year);

    Integer getOrderNumByDate(String date);

    List<CountOrderNum> CountOrderNumByDate();

    List<Order> countOrderList(Integer orderId);
}
