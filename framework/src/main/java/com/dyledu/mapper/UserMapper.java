package com.dyledu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyledu.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User selectByIdTest(@Param("id") Long id);
}
