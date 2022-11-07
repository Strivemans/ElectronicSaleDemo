package com.jack.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.demo.entity.Account;
import com.jack.demo.entity.Client;
import com.jack.demo.entity.CountClientNum;

import java.util.List;

public interface ClientService extends IService<Client> {
    Client getClientByName(String clientName);

    Client getClientById(Integer clientId);

    Integer getClientNumByYear(String year);

    Integer getClientByDate(String date);

    List<CountClientNum> getClientNumByDate();
}
