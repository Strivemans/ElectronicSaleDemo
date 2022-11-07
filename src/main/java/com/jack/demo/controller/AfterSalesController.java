package com.jack.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jack.demo.common.R;
import com.jack.demo.entity.AfterDetail;
import com.jack.demo.entity.AfterSales;
import com.jack.demo.service.AfterSalesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * \* User: jack
 * \* Date: 2022/10/21
 * \* Time: 22:50
 * \* Description: 售后控制层
 */
@RestController
@CrossOrigin
@RequestMapping("/after")
@Api("售后管理")
public class AfterSalesController {
    @Resource
    private AfterSalesService afterSalesService;

    @PostMapping("add")
    @ApiOperation("添加售后")
    public R add(@ApiParam(value = "afterSales",name = "售后类",required = true) @RequestBody AfterSales afterSales){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        afterSales.setCreateTime(date);

        boolean result = afterSalesService.save(afterSales);
        return result?R.ok().message("添加成功"):R.error().message("添加失败");
    }

    @GetMapping("getAll")
    @ApiOperation("获取售后列表")
    public R getAll(@ApiParam(value = "orderId",name = "订单id",required = false) @RequestParam(required = false) Integer orderId,
                    @ApiParam(value = "state",name = "售后状态",required = false) @RequestParam(required = false) String state){
        List<AfterSales> afterList = afterSalesService.getAfterList(orderId, state);
        return R.ok().message("查询成功").data("list",afterList);
    }

    @PostMapping("update")
    @ApiOperation("修改售后信息")
    public R update(@ApiParam(value = "afterSales",name = "售后类",required = true) @RequestBody AfterSales after){
        UpdateWrapper<AfterSales> afterSalesUpdateWrapper = new UpdateWrapper<>();
        // 如果售后已完成 添加结束时间
        if(after.getAfterState().equals("已完成")){
            after.setEndTime(new Date(System.currentTimeMillis()));
        }else{
            afterSalesUpdateWrapper.set("end_time",null);
        }
        afterSalesUpdateWrapper.eq("order_id",after.getOrderId());
        boolean result = afterSalesService.update(after, afterSalesUpdateWrapper);
        return result?R.ok().message("修改成功"):R.error().message("修改失败");
    }

    @GetMapping("delete")
    @ApiOperation("删除售后")
    public R delete(@ApiParam(value = "orderId",name = "订单id",required = true) @RequestParam Integer orderId){
        QueryWrapper<AfterSales> afterWrapper = new QueryWrapper<>();
        afterWrapper.eq("order_id",orderId);
        boolean result = afterSalesService.remove(afterWrapper);
        return result?R.ok().message("删除成功"):R.error().message("删除失败");
    }

    @GetMapping("getDetail")
    @ApiOperation("查询售后详情信息")
    public R getDetail(@ApiParam(value = "afterId",name = "售后id",required = true) @RequestParam Integer afterId){
        AfterDetail afterDetail = afterSalesService.getAfterDetail(afterId);
        return R.ok().message("查询成功").data("list",afterDetail);
    }

    @GetMapping("getByOrderId")
    @ApiOperation("根据订单id查询售后基本信息")
    public R getByOrderId(@ApiParam(value = "orderId",name = "订单id",required = true) @RequestParam Integer orderId){
        AfterSales after = afterSalesService.getAfterByOrderId(orderId);
        return R.ok().message("查询成功").data("after",after);
    }
}
