package com.jack.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.demo.entity.Good;
import com.jack.demo.entity.Saler;

import java.util.HashMap;
import java.util.List;

public interface GoodService extends IService<Good> {
    Good getGoodByName(String goodName);

    void updateGoodStockAndSales(HashMap<Integer,Integer> goodMap);

    Good getGoodById(Integer goodId);

    void updateGood(Good good);

    List<Good> getGoodList();

    List<Good> rankGoodList();


}
