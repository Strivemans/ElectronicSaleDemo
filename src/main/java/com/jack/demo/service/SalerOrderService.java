package com.jack.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.demo.entity.Category;
import com.jack.demo.entity.SalerOrder;

import java.util.List;

public interface SalerOrderService extends IService<SalerOrder> {
    void delSalerOrder(Integer orderId);

    List<SalerOrder> getSakerProfit(String salerName);
}
