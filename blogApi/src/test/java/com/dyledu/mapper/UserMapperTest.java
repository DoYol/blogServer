package com.dyledu.mapper;

import com.dyledu.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUserMapperXml(){
        Long id=1L;
        User user = userMapper.selectByIdTest(id);
        log.info(user.toString());
    }
}
