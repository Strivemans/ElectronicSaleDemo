package com.jack.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.demo.entity.Account;
import com.jack.demo.entity.Client;
import com.jack.demo.entity.CountClientNum;
import com.jack.demo.mapper.AccountMapper;
import com.jack.demo.mapper.ClientMapper;
import com.jack.demo.service.AccountService;
import com.jack.demo.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * \* User: jack
 * \* Date: 2022/10/15
 * \* Time: 14:56
 * \* Description: 客户类实现层
 */
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client> implements ClientService {
    /**
     * 根据姓名 查询 客户信息
     * @param clientName : 客户名
     */
    @Override
    public Client getClientByName(String clientName) {
        QueryWrapper<Client> clientQueryWrapper = new QueryWrapper<>();
        clientQueryWrapper.eq("client_name",clientName);
        Client client = baseMapper.selectOne(clientQueryWrapper);
        return client;
    }

    @Override
    public Client getClientById(Integer clientId) {
        QueryWrapper<Client> clientQueryWrapper = new QueryWrapper<>();
        clientQueryWrapper.eq("client_id",clientId);
        Client client = baseMapper.selectOne(clientQueryWrapper);
        return client;
    }

    /**
     * 根据年份 获取 客流量
     * @param year
     * @return
     */
    @Override
    public Integer getClientNumByYear(String year) {
        QueryWrapper<Client> clientQueryWrapper = new QueryWrapper<>();
        clientQueryWrapper.like("create_date","%"+year+"%");
        clientQueryWrapper.select("count(*)");
        Integer clientNum = baseMapper.selectCount(clientQueryWrapper);
        return clientNum;
    }

    /**
     * 获取今天的新增用户数
     * @param date ： 日期
     * @return
     */
    @Override
    public Integer getClientByDate(String date) {
        QueryWrapper<Client> clientQueryWrapper = new QueryWrapper<>();
        clientQueryWrapper.eq("create_date",date);
        clientQueryWrapper.select("count(*)");
        Integer todayClientNum = baseMapper.selectCount(clientQueryWrapper);
        return todayClientNum;
    }

    /**
     * 根据创建时间 统计用户数
     * @return
     */
    @Override
    public List<CountClientNum> getClientNumByDate() {
        QueryWrapper<Client> clientQueryWrapper = new QueryWrapper<>();
        clientQueryWrapper.select("distinct create_date");
        List<Client> dates = baseMapper.selectList(clientQueryWrapper);
        List<Client> clients = baseMapper.selectList(null);
        List<CountClientNum> list = new ArrayList<>();
        for(Client date:dates){
            CountClientNum countClientNum = new CountClientNum();
            countClientNum.setDate(date.getCreateDate());
            Integer num = 0;
            for(Client client:clients){
                //统计这个时间有多少用户
                if(client.getCreateDate().equals(date.getCreateDate())){
                    num++;
                }
            }
            countClientNum.setNum(num);
            list.add(countClientNum);
        }
        return list;
    }
}
