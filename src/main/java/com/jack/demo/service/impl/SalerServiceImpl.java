package com.jack.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.demo.entity.Account;
import com.jack.demo.entity.Saler;
import com.jack.demo.mapper.AccountMapper;
import com.jack.demo.mapper.SalerMapper;
import com.jack.demo.service.AccountService;
import com.jack.demo.service.SalerOrderService;
import com.jack.demo.service.SalerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * \* User: jack
 * \* Date: 2022/10/15
 * \* Time: 14:56
 * \* Description: 员工类实现层
 */
@Service
public class SalerServiceImpl extends ServiceImpl<SalerMapper, Saler> implements SalerService {
    @Resource
    private SalerOrderService salerOrderService;
    /**
     * 根据姓名查询销售员信息
     * @param salerName : 销售员名
     * @return : 返回对应的销售员信息
     */
    @Override
    public Saler getSalerByName(String salerName) {
        QueryWrapper<Saler> salerQueryWrapper = new QueryWrapper<>();
        salerQueryWrapper.eq("saler_name", salerName);
        Saler saler = baseMapper.selectOne(salerQueryWrapper);
        return saler;
    }

    /**
     * 根据销售员id 返回 销售员信息
     * @param salerId ： 销售员id
     * @return ： 对应的销售员信息
     */
    @Override
    public Saler getSalerById(Integer salerId) {
        QueryWrapper<Saler> salerQueryWrapper = new QueryWrapper<>();
        salerQueryWrapper.eq("saler_id",salerId);
        Saler saler = baseMapper.selectOne(salerQueryWrapper);
        return saler;
    }

    /**
     * 更新销售员信息
     * @param saler ：需要更新的销售员信息
     */
    @Override
    public void updateSaler(Saler saler) {
        QueryWrapper<Saler> salerQueryWrapper = new QueryWrapper<>();
        salerQueryWrapper.eq("saler_id",saler.getSalerId());
        baseMapper.update(saler,salerQueryWrapper);
    }

}
