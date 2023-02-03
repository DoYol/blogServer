package com.dyledu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyledu.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
