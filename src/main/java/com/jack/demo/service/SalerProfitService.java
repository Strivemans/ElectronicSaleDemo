package com.jack.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.demo.entity.Account;
import com.jack.demo.entity.SalerProfit;

public interface SalerProfitService extends IService<SalerProfit> {
    void updateProfitByOrderId(SalerProfit salerProfit);

    SalerProfit getByOrderId(Integer orderId);

    void delByOrderId(Integer orderId);
}
