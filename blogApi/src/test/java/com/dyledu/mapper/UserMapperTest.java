package com.dyledu.mapper;

import com.dyledu.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@SpringBootTest
@Slf4j
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;


    public void testUserMapperXml(){
        User user = userMapper.selectTest();
        log.info(user.toString());
    }

    public void testUserMapper(){
        String fileName="abcde测试测试.png";
        //根据日期生成路径   2022/1/15/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String datePath = sdf.format(new Date());
        //uuid作为文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //后缀和文件后缀一致
        int index = fileName.lastIndexOf(".");
        // test.jpg -> .jpg
        String fileType = fileName.substring(index);
        log.info(new StringBuilder().append(datePath).append(uuid).append(fileType).toString());
    }
}
