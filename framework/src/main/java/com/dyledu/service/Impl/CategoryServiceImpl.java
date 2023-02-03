package com.dyledu.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyledu.constans.SystemConstants;
import com.dyledu.domain.ResponseResult;
import com.dyledu.domain.entity.Category;
import com.dyledu.domain.vo.CategoryListVo;
import com.dyledu.mapper.CategoryMapper;
import com.dyledu.service.CategoryService;
import com.dyledu.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Override
    public ResponseResult getCategoryList() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus, SystemConstants.STATUS_NORMAL);
        List<Category> categoryList = this.list(queryWrapper);

        List<CategoryListVo> categoryListVo = BeanCopyUtils.copyBeanList(categoryList, CategoryListVo.class);

        return ResponseResult.okResult(categoryListVo);
    }
}
