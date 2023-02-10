package com.dyledu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                设置允许跨域请求的域名
                .allowedOriginPatterns("*")
//                是否允许使用Cookie
                .allowCredentials(true)
//                跨域允许时间
                .maxAge(300 * 1000)
//                设置允许的header属性
                .allowedHeaders("*")
//                设置允许的请求方式
                .allowedMethods("*");
    }
}
