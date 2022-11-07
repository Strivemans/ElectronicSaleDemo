package com.jack.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.demo.entity.Account;
import com.jack.demo.entity.Saler;

import java.util.List;

public interface SalerService extends IService<Saler> {
    Saler getSalerByName(String salerName);

    Saler getSalerById(Integer salerId);

    void updateSaler(Saler saler);
}
