package com.jack.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.demo.common.R;
import com.jack.demo.entity.Saler;
import com.jack.demo.entity.SalerOrder;
import com.jack.demo.service.SalerOrderService;
import com.jack.demo.service.SalerService;
import com.jack.demo.utils.GetWeekDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * \* User: jack
 * \* Date: 2022/10/17
 * \* Time: 10:09
 * \* Description: 销售员的控制类
 */
@RestController
@RequestMapping("/saler")
@CrossOrigin
@Api("销售员管理")
public class SalerController {
    @Resource
    private SalerService salerService;
    @Resource
    private SalerOrderService salerOrderService;

    @GetMapping("getWeekDate")
    @ApiOperation("返回这一星期的日期")
    public R getWeekDate(@ApiParam(value = "date",name = "日期",required = true) @RequestParam String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj = null;
        try {
            dateObj = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String[] weekDays = GetWeekDate.getWeekDays(dateObj);
        return R.ok().message("查询成功").data("list",weekDays);
    }

    @GetMapping("getSalerProfitList")
    @ApiOperation("获取销售员业绩列表")
    public R getSalerProfitList(@ApiParam(value = "salerName",name = "销售员",required = false) @RequestParam(required = false) String salerName){
        List<SalerOrder> sakerProfit = salerOrderService.getSakerProfit(salerName);
        return R.ok().message("查询成功").data("list",sakerProfit);
    }

    @GetMapping("getInfo")
    @ApiOperation("根据Id获取销售员信息")
    public R getInfo(@ApiParam(name = "salerId",value = "销售员Id",required = true) @RequestParam Integer salerId){
        QueryWrapper<Saler> salerQueryWrapper = new QueryWrapper<>();
        salerQueryWrapper.eq("saler_id",salerId);
        Saler saler = salerService.getOne(salerQueryWrapper);
        setPosition(saler);
        return saler!=null?R.ok().message("查询成功").data("saler",saler):R.error().message("没有该员工");
    }

    @PostMapping("add")
    @ApiOperation("添加员工")
    public R add(@ApiParam(name = "saler",value = "员工",required = true) @RequestBody Saler saler){
        boolean result = salerService.save(saler);
        return result?R.ok().message("添加成功"):R.error().message("添加失败");
    }

    @GetMapping("findAll")
    @ApiOperation("获取所有员工信息")
    public R getAll(@ApiParam(name = "salerName",value = "员工名",required = false) @RequestParam(required = false) String salerName,
                    @ApiParam(name="salerStatus",value = "员工职位",required = false) @RequestParam(required = false) Integer status){
        QueryWrapper<Saler> salerQueryWrapper = new QueryWrapper<>();
        List<Saler> salerList = new ArrayList<>();
        if(salerName!=null && status!=null){
            salerQueryWrapper.eq("saler_name",salerName);
            salerQueryWrapper.eq("permission_id",status);
        }else if(salerName!=null && status==null){
            salerQueryWrapper.eq("saler_name",salerName);
        }else if(salerName==null && status!=null){
            salerQueryWrapper.eq("permission_id",status);
        }
        salerList = salerService.list(salerQueryWrapper);
        for(Saler saler: salerList){
            if(saler.getPosition()==null){
                setPosition(saler);
            }
        }
        return R.ok().message("查询成功").data("list",salerList);
    }

    @PostMapping("update")
    @ApiOperation("更新员工信息")
    public R update(@ApiParam(name = "saler",value = "员工",required = true) @RequestBody Saler saler){
        QueryWrapper<Saler> salerQueryWrapper = new QueryWrapper<>();
        salerQueryWrapper.eq("saler_id",saler.getSalerId());
        boolean result = salerService.update(saler, salerQueryWrapper);
        return result?R.ok().message("修改成功"):R.error().message("修改失败");
    }

    @GetMapping("delete")
    @ApiOperation("删除员工")
    public R delete(@ApiParam(name = "salerId",value = "员工id",required = true) @RequestParam Integer salerId){
        QueryWrapper<Saler> salerQueryWrapper = new QueryWrapper<>();
        salerQueryWrapper.eq("saler_id",salerId);
        boolean result = salerService.remove(salerQueryWrapper);
        return result?R.ok().message("删除成功"):R.error().message("删除失败");
    }

    /**
     * 根据权限id 设置 对应的职位
     * @param saler: 销售员类
     */
    public void setPosition(Saler saler){
        if(saler.getPermissionId() == 1) {
            saler.setPosition("店主");
        }else if(saler.getPermissionId() == 2){
            saler.setPosition("销售员");
        }else{
            saler.setPosition("跟单员");
        }
    }
}
