package com.jack.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.demo.entity.Account;
import com.jack.demo.entity.Saler;
import com.jack.demo.entity.UpdateAccount;
import com.jack.demo.mapper.AccountMapper;
import com.jack.demo.service.AccountService;
import com.jack.demo.service.SalerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * \* User: jack
 * \* Date: 2022/10/15
 * \* Time: 14:56
 * \* Description: 账户类实现层
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {
    @Resource
    private SalerService salerService;

    /**
     * 更新个人中心的信息
     * @param updateAccount : 更新的账户内容
     */
    @Override
    public void updateAccountInfo(UpdateAccount updateAccount) {
        //给销售员信息赋值
        Saler saler = salerService.getSalerById(updateAccount.getSalerId());
        saler.setSalerName(updateAccount.getName());
        saler.setAddress(updateAccount.getAddress());
        saler.setSalerTel(updateAccount.getTelephone());
        saler.setSignatur(updateAccount.getSignatur());

        //给账号信息赋值
        Account account = getAccountById(updateAccount.getAccountId());
        account.setUserName(updateAccount.getAccountName());
        account.setPassword(updateAccount.getPassword());

        //对两部分进行更新
        salerService.updateSaler(saler);
        updateAccount(account);
    }

    /**
     * 根据账号id 获取 账号信息
     * @param accountId : 账号id
     * @return 返回账号类
     */
    @Override
    public Account getAccountById(Integer accountId) {
        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();
        accountQueryWrapper.eq("id",accountId);
        Account account = baseMapper.selectOne(accountQueryWrapper);
        return account;
    }

    /**
     * 更新账户信息
     * @param account : 需要更新的账户
     */
    @Override
    public void updateAccount(Account account) {
        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();
        accountQueryWrapper.eq("id",account.getId());
        baseMapper.update(account,accountQueryWrapper);
    }

    /**
     * 根据销售员id 获取 账户中心信息
     * @param salerId : 销售员id
     * @return ： 账户中心信息
     */
    @Override
    public UpdateAccount getAccountInfo(Integer salerId) {
        UpdateAccount updateAccount = new UpdateAccount();
        //1. 获取销售员基本信息
        Saler saler = salerService.getSalerById(salerId);
        updateAccount.setSalerId(salerId);
        updateAccount.setAddress(saler.getAddress());
        updateAccount.setName(saler.getSalerName());
        updateAccount.setTelephone(saler.getSalerTel());
        updateAccount.setSignatur(saler.getSignatur());

        //2. 设置账户信息
        Account account = getAccountBuSalerId(salerId);
        updateAccount.setAccountId(account.getId());
        updateAccount.setAccountName(account.getUserName());
        updateAccount.setPassword(account.getPassword());

        return updateAccount;
    }

    /**
     * 根据销售员id 获取 账户信息
     * @param salerId : 销售员id
     * @return ： 账户信息
     */
    @Override
    public Account getAccountBuSalerId(Integer salerId) {
        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();
        accountQueryWrapper.eq("saler_id",salerId);
        Account account = baseMapper.selectOne(accountQueryWrapper);
        return account;
    }
}
