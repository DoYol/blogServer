package com.dyledu.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class RoleMapperTest {
    @Autowired
    private RoleMapper roleMapper;


    public void textRoleID(){
        String role = roleMapper.selectRoleByUserID(1L);
        log.info(role);
    }
}
