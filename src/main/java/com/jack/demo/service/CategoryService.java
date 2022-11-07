package com.jack.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.demo.entity.Account;
import com.jack.demo.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    void pageQuery(Page<Category> page,String categoryName);

    Boolean isExistGood(Integer categoryId);

    List<Category> percentCategory();
}
