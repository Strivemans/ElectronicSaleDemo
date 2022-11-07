package com.jack.demo.controller;

import com.jack.demo.common.R;
import com.jack.demo.entity.Client;
import com.jack.demo.service.ClientService;
import com.jack.demo.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * \* User: jack
 * \* Date: 2022/10/26
 * \* Time: 10:29
 * \* Description:店铺控制层
 */
@RestController
@RequestMapping("/shop")
@Api("店铺管理")
@CrossOrigin
public class ShopController {
    @Resource
    private OrderService orderService;
    @Resource
    private ClientService clientService;

    @GetMapping("totalMes")
    @ApiOperation("获取店铺年度信息")
    public R getTotalSales(@ApiParam(value = "year",name = "年",required = true) @RequestParam String date){
        // 获取年
        String[] dateArray = date.split("-");
        String year = dateArray[0];

        // 获取本年的销售额
        Double currentTotalSales = orderService.getTotalSaleByYear(year);
        // 获取上年的销售额
        Integer lastYear = Integer.parseInt(year) - 1;
        Double lastTotalSales = orderService.getTotalSaleByYear(lastYear.toString());
        // 计算与上年的比率值
        Double ratio = (((currentTotalSales - lastTotalSales) / lastTotalSales) * 100);
        Integer ratioObj = ratio.intValue();
        // 日均销售额
        Double daySale =(double)((Math.round((currentTotalSales / 365) * 100)) / 100);
        // 获取年客户数
        Integer clientNumByYear = clientService.getClientNumByYear(year);
        // 今日客户数
        Integer todayClientNum = clientService.getClientByDate(date);
        // 支付笔数
        Integer orderByPay = orderService.getOrderByPay(year);
        // 今日支付数
        Integer todayOrderByPay = orderService.getOrderNumByDate(date);
        // 规整信息
        Map<String,Number> showMes = new HashMap<>();
        showMes.put("totalSale",currentTotalSales);
        showMes.put("ratio",ratioObj);
        showMes.put("daySale",daySale);
        showMes.put("clientNum",clientNumByYear);
        showMes.put("orderByPay",orderByPay);
        showMes.put("todayClientNum",todayClientNum);
        showMes.put("todayOrderByPay",todayOrderByPay);
        return R.ok().message("查询成功").data("totalMes",showMes);
    }

    @GetMapping("todayOrder")
    @ApiOperation("获取今天的订单数")
    public R getTodayOrder(@ApiParam(value = "date",name = "日期",required = true) @RequestParam String date){
        Integer orderNumByDate = orderService.getOrderNumByDate(date);
        return R.ok().message("获取成功").data("num",orderNumByDate);
    }
}
