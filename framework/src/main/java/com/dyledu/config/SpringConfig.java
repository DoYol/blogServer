package com.dyledu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class SpringConfig extends WebMvcConfigurationSupport {
    /**
     * 扩展MVC的消息转换器
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        创建消息转换器对象
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//        设置对象转换器，将Java对象转换为json
        jackson2HttpMessageConverter.setObjectMapper(new JacksonObjectMapper());
//        将配置的消息转换器对象，配置到mvc框架的转换器集合中
        converters.add(0,jackson2HttpMessageConverter);
    }
}
