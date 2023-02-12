package com.dyledu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyledu.domain.entity.Role;
import com.dyledu.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    String selectRoleByUserID(@Param("id") Long id);
}
