package com.jack.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.demo.entity.Account;
import com.jack.demo.entity.AfterDetail;
import com.jack.demo.entity.AfterSales;

import java.util.List;

public interface AfterSalesService extends IService<AfterSales> {
    List<AfterSales> getAfterList(Integer orderId,String state);

    AfterDetail getAfterDetail(Integer afterId);

    void delAfterByOrderId(Integer orderId);

    AfterSales getAfterByOrderId(Integer orderId);
}
