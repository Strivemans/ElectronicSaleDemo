package com.jack.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.demo.common.R;
import com.jack.demo.entity.*;
import com.jack.demo.service.AccountService;
import com.jack.demo.service.ClientService;
import com.jack.demo.service.SalerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * \* User: jack
 * \* Date: 2022/10/15
 * \* Time: 15:11
 * \* Description: 客户控制层
 */
@RestController
@CrossOrigin
@RequestMapping("/client")
@Api("客户管理")
public class ClientController {
   @Resource
   private ClientService clientService;

   @PostMapping("add")
   @ApiOperation("添加客户")
   public R add(@ApiParam(value = "client",name = "客户",required = true) @RequestBody Client client){
       Date date = new Date(System.currentTimeMillis());
       client.setCreateDate(date);
       boolean result = clientService.save(client);
       return result?R.ok().message("添加成功"):R.error().message("添加失败");
   }

   @GetMapping("getAll")
   @ApiOperation("获取客户列表")
    public R getAll(@ApiParam(value = "clientName",name = "客户名",required = false) @RequestParam(required = false) String clientName,
                    @ApiParam(value = "clientRank",name = "客户等级",required = false) @RequestParam(required = false) Integer clientRank) {
       QueryWrapper<Client> clientQueryWrapper = new QueryWrapper<>();
       List<Client> list  = null;
       if (clientName == null && clientRank == null) {
           list = clientService.list(null);
       }else if(clientName!=null && clientRank == null){
           clientQueryWrapper.eq("client_name",clientName);
           list = clientService.list(clientQueryWrapper);
       }else if(clientName == null && clientRank != null){
           clientQueryWrapper.eq("client_rank",clientRank);
           list = clientService.list(clientQueryWrapper);
       }else if(clientName != null && clientRank != null){
           clientQueryWrapper.eq("client_name",clientName);
           clientQueryWrapper.eq("client_rank",clientRank);
           list = clientService.list(clientQueryWrapper);
       }
       for(Client client:list){
           setRankName(client);
       }
       return R.ok().message("查询成功").data("list",list);
   }

   @PostMapping("update")
   @ApiOperation("修改客户信息")
   public R update(@ApiParam(value = "client",name = "客户类",required = true) @RequestBody Client client){
       QueryWrapper<Client> clientQueryWrapper = new QueryWrapper<>();
       clientQueryWrapper.eq("client_id",client.getClientId());
       boolean result = clientService.update(client, clientQueryWrapper);
       return result?R.ok().message("修改成功"):R.error().message("修改失败");
   }

   @GetMapping("delete")
   @ApiOperation("删除客户")
   public R delete(@ApiParam(value = "clientId",name = "客户id",required = true) @RequestParam Integer clientId){
       QueryWrapper<Client> clientQueryWrapper = new QueryWrapper<>();
       clientQueryWrapper.eq("client_id",clientId);
       boolean result = clientService.remove(clientQueryWrapper);
       return result?R.ok().message("删除成功"):R.error().message("删除失败");
   }

   @GetMapping("getByName")
   @ApiOperation("根据名字查询信息")
   public R getByName(@ApiParam(name = "clientName",value = "客户名",required = true) @RequestParam String clientName){
       QueryWrapper<Client> clientQueryWrapper = new QueryWrapper<>();
       clientQueryWrapper.eq("client_name",clientName);
       Client client = clientService.getOne(clientQueryWrapper);
       return client==null?R.error().message("没有该客户"):R.ok().message("查询成功").data("client",client);
   }

   @GetMapping("getCountByDate")
   @ApiOperation("根据日期统计用户数")
   public R getCountByDate(){
       List<CountClientNum> clientNumByDate = clientService.getClientNumByDate();
       return R.ok().message("查询成功").data("list",clientNumByDate);
   }

    /**
     * 根据不同的vip等级 设置 会员名
     * @param client : 客户类
     */
   public void setRankName(Client client){
       if(client.getClientRank() == 1){
           client.setRankName("普通会员");
       }else if(client.getClientRank() == 2){
           client.setRankName("黄金会员");
       }else {
           client.setRankName("铂金会员");
       }
   }
}
