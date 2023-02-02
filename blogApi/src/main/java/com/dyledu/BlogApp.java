package com.dyledu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@Slf4j
@SpringBootApplication
@ServletComponentScan
public class BlogApp {
    public static void main(String[] args) {
        SpringApplication.run(BlogApp.class,args);
        log.info("Blog项目启动成功！");
    }
}
