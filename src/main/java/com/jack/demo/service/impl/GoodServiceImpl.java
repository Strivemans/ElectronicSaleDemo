package com.jack.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.demo.entity.Good;
import com.jack.demo.entity.Saler;
import com.jack.demo.mapper.GoodMapper;
import com.jack.demo.mapper.SalerMapper;
import com.jack.demo.service.GoodService;
import com.jack.demo.service.SalerService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * \* User: jack
 * \* Date: 2022/10/15
 * \* Time: 14:56
 * \* Description: 产品类实现层
 */
@Service
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements GoodService {

    /**
     * 根据产品名 获取 产品信息
     * @param goodName 产品名
     * @return 对应的产品类
     */
    @Override
    public Good getGoodByName(String goodName) {
        QueryWrapper<Good> goodQueryWrapper = new QueryWrapper<>();
        goodQueryWrapper.eq("good_name",goodName);
        Good good = baseMapper.selectOne(goodQueryWrapper);
        return good;
    }

    /**
     * 修改产品的库存 和 销售量
     * @param goodMap: 传入多个产品信息 修改产品的库存 key：产品id value: 购买的数量
     */
    @Override
    public void updateGoodStockAndSales(HashMap<Integer, Integer> goodMap) {
        for(Map.Entry<Integer,Integer> entry : goodMap.entrySet()){
            Integer goodId = entry.getKey();
            Good good = getGoodById(goodId);
            Integer remainNum = good.getGoodStock() - entry.getValue();
            Integer sales = good.getGoodInventory() + entry.getValue();
            if(remainNum<0){
                remainNum = 0;
            }
            good.setGoodStock(remainNum); //更新 库存
            good.setGoodInventory(sales); //更新 销售量
            updateGood(good);
        }
    }

    /**
     * 根据产品id 查询 产品
     * @param goodId ： 产品id
     * @return ：返回查询到的产品
     */
    @Override
    public Good getGoodById(Integer goodId) {
        QueryWrapper<Good> goodQueryWrapper = new QueryWrapper<>();
        goodQueryWrapper.eq("good_id",goodId);
        Good good = baseMapper.selectOne(goodQueryWrapper);
        return good;
    }

    /**
     * 修改产品信息
     * @param good ： 修改的good
     */
    @Override
    public void updateGood(Good good) {
        QueryWrapper<Good> goodQueryWrapper = new QueryWrapper<>();
        goodQueryWrapper.eq("good_id",good.getGoodId());
        baseMapper.update(good,goodQueryWrapper);
    }

    /**
     * 获取订单列表
     * @return : 返回订单列表
     */
    @Override
    public List<Good> getGoodList() {
        List<Good> goodList = baseMapper.selectList(null);
        return goodList;
    }

    /**
     * 根据销售量 降序查询 产品列表
     * @return
     */
    @Override
    public List<Good> rankGoodList() {
        QueryWrapper<Good> goodQueryWrapper = new QueryWrapper<>();
        goodQueryWrapper.orderByDesc("good_inventory");
        List<Good> goodList = baseMapper.selectList(goodQueryWrapper);
        return goodList;
    }
}
