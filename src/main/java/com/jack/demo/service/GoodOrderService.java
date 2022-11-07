package com.jack.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.demo.entity.Category;
import com.jack.demo.entity.Good;
import com.jack.demo.entity.GoodOrder;

import java.util.HashMap;
import java.util.List;

public interface GoodOrderService extends IService<GoodOrder> {
    void OrderRelGood(Integer orderId, List<HashMap<String,Integer>> goodAndNum,List<HashMap<Integer,Double>> goodAndPrice);

    void deleteGoodOrder(Integer orderId);

    List<GoodOrder> findGoodOrderListByOrderId(Integer orderId);

    List<Good> getGoodListByOrderId(Integer orderId);

    List<GoodOrder> getGoodOrderByOrderId(Integer orderId);

    Boolean isStockOutByOrderId(Integer orderId);

    void updateGoodOrder(GoodOrder goodOrder);
}
