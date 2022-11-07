package com.jack.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jack.demo.common.R;
import com.jack.demo.entity.Category;
import com.jack.demo.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * \* User: jack
 * \* Date: 2022/10/17
 * \* Time: 14:40
 * \* Description: 产品分类控制层
 */
@RestController
@CrossOrigin
@RequestMapping("/category")
@Api("产品分类管理")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @PostMapping("add")
    @ApiOperation("添加产品分类")
    private R add(@ApiParam(value = "category",name = "产品分类",required = true) @RequestBody Category category){
        boolean result = categoryService.save(category);
        return result?R.ok().message("添加成功"):R.error().message("添加失败");
    }

    @GetMapping("findAll")
    @ApiOperation("查询所有分类信息")
    public R findAll(@ApiParam(value = "categoryName",name = "分类名",required = false) @RequestParam(required = false) String categoryName){
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        if(categoryName!=null){
            categoryQueryWrapper.like("species_name",categoryName);
            List<Category> categoryList = categoryService.list(categoryQueryWrapper);
            return categoryList!=null?R.ok().message("查询成功").data("list",categoryList):R.error().message("查询失败");
        }else {
            List<Category> list = categoryService.list(null);
            return list!=null?R.ok().message("查询成功").data("list",list):R.error().message("查询失败");
        }
    }

    @PostMapping("update")
    @ApiOperation("修改分类")
    public R update(@ApiParam(value = "category",name = "分类",required = true) @RequestBody Category category){
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.eq("species_id",category.getSpeciesId());
        boolean result = categoryService.update(category, categoryQueryWrapper);
        return result?R.ok().message("修改成功"):R.error().message("修改失败");
    }

    @GetMapping("deleteById")
    @ApiOperation("删除分类")
    public R delete(@ApiParam(value = "categoryId",name = "分类id",required = true) @RequestParam Integer categoryId){
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.eq("species_id",categoryId);
        boolean result = categoryService.remove(categoryQueryWrapper);
        return result?R.ok().message("修改成功"):R.error().message("修改失败");
    }


    @GetMapping("isExistsGood")
    @ApiOperation("产品是否关联此分类")
    public R isExistsGood(@ApiParam(value = "categoryId",name = "分类id",required = true) @RequestParam Integer categoryId){
        Boolean result = categoryService.isExistGood(categoryId);
        return R.ok().message("查询成功").data("result",result);
    }
}
