package com.dyledu.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})          //该注解可以加在哪些东西上面
@Retention(RetentionPolicy.RUNTIME)  //该注解保持在哪个阶段
public @interface SystemLog {
    String businessName();
}
