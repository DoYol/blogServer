package com.dyledu.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyledu.domain.entity.Menu;
import com.dyledu.mapper.MenuMapper;
import com.dyledu.service.MenuService;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
}
