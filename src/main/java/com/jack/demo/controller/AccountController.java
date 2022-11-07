package com.jack.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.demo.common.R;
import com.jack.demo.entity.Account;
import com.jack.demo.entity.Register;
import com.jack.demo.entity.Saler;
import com.jack.demo.entity.UpdateAccount;
import com.jack.demo.service.AccountService;
import com.jack.demo.service.OssService;
import com.jack.demo.service.SalerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * \* User: jack
 * \* Date: 2022/10/15
 * \* Time: 15:11
 * \* Description: 账号控制层
 */
@RestController
@CrossOrigin
@RequestMapping("/account")
@Api("账号管理")
public class AccountController {
    @Resource
    private AccountService accountService;
    @Resource
    private SalerService salerService;

    @PostMapping("update")
    @ApiOperation("修改账号相关信息")
    public R update(@ApiParam(value = "updateAccount",name = "账号更新类",required = true) @RequestBody UpdateAccount updateAccount){
        accountService.updateAccountInfo(updateAccount);
        return R.ok().message("修改成功");
    }

    @GetMapping("getAccountInfo")
    @ApiOperation("获取个人中心信息")
    public R getAccountInfo(@ApiParam(value = "salerId",name = "销售员id",required = true) @RequestParam Integer salerId){
        UpdateAccount accountInfo = accountService.getAccountInfo(salerId);
        return R.ok().message("查询成功").data("info",accountInfo);
    }

    @GetMapping("getById")
    @ApiOperation("根据销售员id查询账号信息")
    public R getById(@ApiParam(name = "salerId",value = "销售员id",required = true) @RequestParam Integer salerId){
        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();
        accountQueryWrapper.eq("saler_id",salerId);
        Account account = accountService.getOne(accountQueryWrapper);
        return account!=null?R.ok().message("获取成功").data("account",account):R.error().message("没有该账号");
    }

    @PostMapping("register")
    @ApiOperation("账号注册")
    /**
     * 账号注册：
     * 注册账号 意味着 店铺新增了新的员工
     *   因此 需要先添加一名 员工信息 然后再 添加账号
     */
    public R register(@RequestBody Register register){
        Saler newSaler = null; //新员工
        Saler existsSaler = null;  //已存在员工
        //1. 查询是否存在该 员工信息 不存在 则 创建员工
        QueryWrapper<Saler> salerExistsWrapper = new QueryWrapper<>();
        salerExistsWrapper.eq("saler_name",register.getSalerName());
        existsSaler = salerService.getOne(salerExistsWrapper);

        //2. 分情况处理
        if(existsSaler == null){
             //不存在 则创建员工
            Saler saler = new Saler(register.getSalerName(),register.getSalerAddress(),register.getSalerTel()
                    ,2);
            //2. 添加到数据库
            salerService.save(saler);
            //3. 获取新添加的员工的id
            QueryWrapper<Saler> salerQueryWrapper = new QueryWrapper<>();
            salerQueryWrapper.select("saler_id");
            salerQueryWrapper.eq("saler_name",saler.getSalerName());
            newSaler = salerService.getOne(salerQueryWrapper);
        }else{
            newSaler = existsSaler;
        }

        //4. 创建新的账号
        Account account = new Account(register.getAccountName(), register.getAccountPwd(), newSaler.getSalerId());
        boolean result = accountService.save(account);

        //5. 给销售员添加 accountId字段
        Account newAccount = getAccountByName(register.getAccountName());
        newSaler.setAccountId(newAccount.getId());
        QueryWrapper<Saler> updateSaleWrapper = new QueryWrapper<>();
        updateSaleWrapper.eq("saler_id",newSaler.getSalerId());
        salerService.update(newSaler,updateSaleWrapper);

        return result ? R.ok().message("注册成功"):R.error().message("注册失败");
    }

    @GetMapping("login")
    @ApiOperation("账号登录")
    /**
     * 账号登录
     */
    public R login(@ApiParam(name = "accountName",value = "账号名",required = false) @RequestParam(required = false) String accountName,
                   @ApiParam(name = "password",value = "密码",required = false) @RequestParam(required = false) String password,
                   @ApiParam(name = "telephone",value = "手机号",required = false) @RequestParam(required = false) String telephone ){
        //使用账号密码登录
       if(accountName!=null && password!=null){
           List list = loginByAccount(accountName, password);
           Integer result = (Integer)list.get(1);
           if(result==0){
               return R.ok().message("没有该账号");
           } else if (result==2) {
               return R.ok().message("密码错误");
           }else {
               Account account = (Account)list.get(0);
               return R.ok().message("登录成功").data("salerId",account.getSalerId());
           }
       }else {
           //使用 手机号登录
           int salerId = loginByTel(telephone);
           return salerId==0? R.ok().message("没有该手机号"):R.ok().message("登录成功").data("salerId",salerId);
       }
    }


    /**
     * 手机号登录
     */
    public int loginByTel(String telephone){
        QueryWrapper<Saler> salerQueryWrapper = new QueryWrapper<>();
        salerQueryWrapper.eq("saler_tel",telephone);
        Saler saler = salerService.getOne(salerQueryWrapper);
        return saler.getSalerId();
    }


    /**
     * 使用账号登录
     * @param userName : 账号名
     * @param password：密码
     * @return： 返回一个集合list list.get(0) 为 查询到的账号类
     *                          list.get(1): 得到的结果 0： 账号为空 1：正确 2：密码错误
     */
    public List loginByAccount(String userName, String password){
        ArrayList<Object> list = new ArrayList<>();
        Account account = getAccountByName(userName);
        list.add(account);
        int result = 0; //结果
        if(account == null){
            result=0;
        }
        if(account.getPassword().equals(password)){
            result=1;
        }else {
            result=2;
        }
        list.add(result);
        return list;
    }

    /**
     * 根据账号名返回对应的账号
     * @param name : 账号名
     * @return 账号
     */
    public Account getAccountByName(String name){
        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();
        accountQueryWrapper.eq("user_name",name);
        Account account = accountService.getOne(accountQueryWrapper);
        return  account;
    }

    /**
     * 账号登录
     */
    @PostMapping("logout")
    public R logout(){
        System.out.println("登出账号");
        return R.ok().message("成功登出");
    }

    /**
     * 获取随机验证码
     * @return
     */
    @PostMapping("getSecurityCode")
    public R getSecurityCode(){
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<=3;i++){
            Integer num = random.nextInt(10);
            stringBuilder.append(num.toString());
        }
        return R.ok().data("securityCode",stringBuilder.toString());
    }
}
