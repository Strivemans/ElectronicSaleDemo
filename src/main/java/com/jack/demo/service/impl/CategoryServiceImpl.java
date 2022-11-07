package com.jack.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.demo.entity.Account;
import com.jack.demo.entity.Category;
import com.jack.demo.entity.Good;
import com.jack.demo.mapper.AccountMapper;
import com.jack.demo.mapper.CategoryMapper;
import com.jack.demo.service.AccountService;
import com.jack.demo.service.CategoryService;
import com.jack.demo.service.GoodService;
import com.jack.demo.utils.Percent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * \* User: jack
 * \* Date: 2022/10/15
 * \* Time: 14:56
 * \* Description: 产品分类实现层
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Resource
    private GoodService goodService;

    @Override
    public void pageQuery(Page<Category> page, String categoryName) {
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        if(categoryName!=null){
            categoryQueryWrapper.like("species_name",categoryName);
            baseMapper.selectPage(page,categoryQueryWrapper);
        }else{
            baseMapper.selectPage(page,null);
        }
    }


    /**
     * 判断该分类 是否 存在产品
     * @param categoryId ： 分类id
     * @return： 返回是否存在
     */
    @Override
    public Boolean isExistGood(Integer categoryId) {
        List<Good> goodList = goodService.getGoodList();
        for(Good good:goodList){
            if(good.getSpeciesId() == categoryId)
                return true;
        }
        return false;
    }

    /**
     * 根据分类占比 获取分类信息
     * @return
     */
    @Override
    public List<Category> percentCategory() {
        QueryWrapper<Good> goodQueryWrapper = new QueryWrapper<>();
        //1. 按照购买类型最多的分类 进行 排序
        goodQueryWrapper.select("species_id,count(*)");
        goodQueryWrapper.groupBy("species_id");
        List<Map<String, Object>> maps = goodService.listMaps(goodQueryWrapper);
        //2. 获取分类信息 计算该分类的占比 添加进集合列表
        List<Category> categories = new ArrayList<>();
        List<Integer> nums = new ArrayList<>(); //存储所有分类的占比数量
        //2.1 获取各个分类占比集合
        for(Map<String,Object> map:maps){
            //整合
            Long num = (Long)map.get("count(*)");
            nums.add(num.intValue());
        }
        //2.2 计算出各分类的占比
        List<String> percents = Percent.percent(nums);
        int index = 0; //分类百分比索引
        //2.3 整合分类信息
        for(Map<String,Object> map:maps){
            Integer speciesId = (Integer)map.get("species_id");
            QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
            categoryQueryWrapper.eq("species_id",speciesId);
            Category category = baseMapper.selectOne(categoryQueryWrapper);
            category.setPercent(percents.get(index++));
            Long num = (Long)map.get("count(*)");
            category.setNum(num.intValue());
            categories.add(category);
        }
        return categories;
    }
}
