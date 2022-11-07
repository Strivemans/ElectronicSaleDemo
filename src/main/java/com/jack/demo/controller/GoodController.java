package com.jack.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.demo.common.R;
import com.jack.demo.entity.Category;
import com.jack.demo.entity.Good;
import com.jack.demo.service.CategoryService;
import com.jack.demo.service.GoodOrderService;
import com.jack.demo.service.GoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * \* User: jack
 * \* Date: 2022/10/17
 * \* Time: 14:33
 * \* Description: 产品实体类
 */
@RestController
@RequestMapping("/good")
@CrossOrigin
@Api("产品管理")
public class GoodController {
    @Resource
    private GoodService goodService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private GoodOrderService goodOrderService;

    @GetMapping("getById")
    @ApiOperation("根据产品id获取产品信息")
    public R getById(@ApiParam(value = "goodId",name = "产品id",required = true) @RequestParam Integer goodId){
        QueryWrapper<Good> goodQueryWrapper = new QueryWrapper<>();
        goodQueryWrapper.eq("good_id",goodId);
        Good good = goodService.getOne(goodQueryWrapper);
        return R.ok().message("查询成功").data("good",good);
    }

    @GetMapping("isStockOut")
    @ApiOperation("判断订单中的产品库存是否充足")
    public R isStockOut(@ApiParam(value = "orderId",name = "订单id",required = true) @RequestParam Integer orderId){
        Boolean result = goodOrderService.isStockOutByOrderId(orderId);
        return R.ok().message("查询成功").data("result",result);
    }

    @GetMapping("rankCategoryList")
    @ApiOperation("获取分类销售排名")
    public R rankCategoryList(){
        List<Category> categories = categoryService.percentCategory();
        return R.ok().message("查询成功").data("list",categories);
    }

    @GetMapping("rankList")
    @ApiOperation("查询产品排名")
    public R RankList(){
        List<Good> goodList = goodService.rankGoodList();
        return R.ok().message("查询成功").data("list",goodList);
    }

    @GetMapping("getOrderGood")
    @ApiOperation("获取订单产品列表")
    public R getOrderGoodList(@ApiParam(value = "orderId",name = "订单id",required = true) @RequestParam Integer orderId){
        List<Good> goodList = goodOrderService.getGoodListByOrderId(orderId);
        return R.ok().message("获取成功").data("list",goodList);
    }

    @PostMapping("add")
    @ApiOperation("添加产品")
    public R add(@ApiParam(value = "good",name = "产品",required = true) @RequestBody Good good){
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.eq("species_name",good.getCategoryName());
        Category category = categoryService.getOne(categoryQueryWrapper);
        good.setSpeciesId(category.getSpeciesId());
        boolean result = goodService.save(good);
        return result?R.ok().message("添加成功"):R.error().message("添加失败");
    }

    @GetMapping("getAll")
    @ApiOperation("查询所有产品")
    public R getAll(@ApiParam(value = "goodName",name = "产品名",required = false) @RequestParam(required = false) String goodName,
                    @ApiParam(value = "categoryId",name = "分类id",required = false) @RequestParam(required = false) Integer categoryId){
        QueryWrapper<Good> goodQueryWrapper = new QueryWrapper<>();
        if(goodName!=null && categoryId!=null){
            goodQueryWrapper.like("good_name",goodName);
            goodQueryWrapper.eq("species_id",categoryId);
        }else if(goodName!=null && categoryId==null){
            goodQueryWrapper.like("good_name",goodName);
        }else if(goodName==null && categoryId!=null){
            goodQueryWrapper.eq("species_id",categoryId);
        }
        List<Good> goodList = goodService.list(goodQueryWrapper);
        setGoodName(goodList);
        return R.ok().message("查询成功").data("list",goodList);
    }

    /**
     * 给每个good产品设置分类名
     * @param goodList :good集合
     */
    public void setGoodName(List<Good> goodList){
        for(Good good:goodList){
            QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
            categoryQueryWrapper.eq("species_id",good.getSpeciesId());
            categoryQueryWrapper.select("species_name");
            Category category = categoryService.getOne(categoryQueryWrapper);
            good.setCategoryName(category.getSpeciesName());
        }
    }

    @PostMapping("update")
    @ApiOperation("修改产品信息")
    public R update(@ApiParam(value = "good",name = "产品类",required = true) @RequestBody Good good){
        QueryWrapper<Good> goodQueryWrapper = new QueryWrapper<>();
        goodQueryWrapper.eq("good_id",good.getGoodId());
        boolean result = goodService.update(good, goodQueryWrapper);
        return result?R.ok().message("修改成功"):R.error().message("修改失败");
    }

    @GetMapping("delete")
    @ApiOperation("删除产品")
    public R delete(@ApiParam(value = "goodId",name = "产品id",required = true) @RequestParam Integer goodId){
        QueryWrapper<Good> goodQueryWrapper = new QueryWrapper<>();
        goodQueryWrapper.eq("good_id",goodId);
        boolean result = goodService.remove(goodQueryWrapper);
        return result?R.ok().message("删除成功"):R.error().message("删除失败");
    }

    @GetMapping("getByName")
    @ApiOperation("根据产品名获取产品信息")
    public R getByName(@ApiParam(value = "goodName",name = "产品名",required = true) @RequestParam String goodName){
        QueryWrapper<Good> goodQueryWrapper = new QueryWrapper<>();
        goodQueryWrapper.eq("good_name",goodName);
        Good good = goodService.getOne(goodQueryWrapper);
        return good==null?R.error().message("获取失败"):R.ok().message("获取成功").data("good",good);
    }
}
