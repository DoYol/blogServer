package com.dyledu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyledu.domain.ResponseResult;
import com.dyledu.domain.entity.Category;

public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();
}
