package com.jack.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.demo.entity.Account;
import com.jack.demo.entity.UpdateAccount;

public interface AccountService extends IService<Account> {
    void updateAccountInfo(UpdateAccount updateAccount);

    Account getAccountById(Integer accountId);

    void updateAccount(Account account);

    UpdateAccount getAccountInfo(Integer salerId);

    Account getAccountBuSalerId(Integer salerId);
}
