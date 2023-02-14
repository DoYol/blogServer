package com.dyledu.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyledu.domain.entity.Role;
import com.dyledu.mapper.RoleMapper;
import com.dyledu.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
