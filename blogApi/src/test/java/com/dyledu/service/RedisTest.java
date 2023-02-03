package com.dyledu.service;

import com.dyledu.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
public class RedisTest {
    @Autowired
    private RedisCache redisCache;

    public void testAdd(){
        redisCache.setCacheObject("text02","测试111");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("张三1",191);
//        map.put("Li2",222);
//        redisCache.setCacheMap("textMap001",map);
    }

    public void testGet(){
        Object text01 = redisCache.getCacheObject("text02");
        log.info(text01.toString());
    }
}
