package com.jack.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.demo.entity.Account;
import com.jack.demo.entity.SalerProfit;
import com.jack.demo.mapper.AccountMapper;
import com.jack.demo.mapper.SalerProfitMapper;
import com.jack.demo.service.AccountService;
import com.jack.demo.service.SalerProfitService;
import org.springframework.stereotype.Service;

/**
 * \* User: jack
 * \* Date: 2022/10/15
 * \* Time: 14:56
 * \* Description: 销售员利润表实现层
 */
@Service
public class SalerProfitServiceImpl extends ServiceImpl<SalerProfitMapper, SalerProfit> implements SalerProfitService {

    /**
     * 根据订单id 修改 产品信息
     * @param salerProfit : 销售利润信息
     */
    @Override
    public void updateProfitByOrderId(SalerProfit salerProfit) {
        QueryWrapper<SalerProfit> salerProfitQueryWrapper = new QueryWrapper<>();
        salerProfitQueryWrapper.eq("order_id",salerProfit.getOrderId());
        baseMapper.update(salerProfit,salerProfitQueryWrapper);
    }

    /**
     * 根据订单id 查询 销售员利润信息
     * @param orderId ：订单id
     * @return ：返回销售员利润表
     */
    @Override
    public SalerProfit getByOrderId(Integer orderId) {
        QueryWrapper<SalerProfit> salerProfitQueryWrapper = new QueryWrapper<>();
        salerProfitQueryWrapper.eq("order_id",orderId);
        SalerProfit salerProfit = baseMapper.selectOne(salerProfitQueryWrapper);
        return salerProfit;
    }

    /**
     * 删除销售利润
     * @param orderId :订单id
     */
    @Override
    public void delByOrderId(Integer orderId) {
        QueryWrapper<SalerProfit> salerProfitQueryWrapper = new QueryWrapper<>();
        salerProfitQueryWrapper.eq("order_id",orderId);
        baseMapper.delete(salerProfitQueryWrapper);
    }
}
