package com.dyledu.service;

import com.dyledu.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class pwdTest {

    public void testPwdMd5(){
        Md5Hash md5Hash = new Md5Hash("123456","yunLong",3);
        log.info(md5Hash.toString());
    }


    public void jwtTest(){
        String sign = JwtUtil.sign("123456789");
        log.info(sign);
    }


    public void jwtTest2(){

        log.info(JwtUtil.parseJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NzYwMjMwNzMsInVzZXJJRCI6IjEyMzQ1Njc4OSJ9.OK5KsifYZAxoSFHsoEf4DKfXP-DBfa_aOiJGMdAn_Ok"));
    }
}
