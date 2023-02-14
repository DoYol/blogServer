package com.dyledu.aspect;

import com.alibaba.fastjson.JSON;
import com.dyledu.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class SystemLogAspect {

    @Pointcut("@annotation(com.dyledu.annotation.SystemLog)")
    public void SystemLogAnnotation() {
    }


    //    定义切面方法
    @Around("SystemLogAnnotation()")
    public Object printLog(ProceedingJoinPoint pj) throws Throwable {
        Object res;
        try {
            handlerBefore(pj);
            res = pj.proceed();
            handlerAfter(res);
        } finally {
            // 结束后换行 System.lineSeparator 系统换行符（兼容各个系统）
            log.info("=======End=======" + System.lineSeparator());
        }
        return res;
    }

    private void handlerBefore(ProceedingJoinPoint pj) {
//RequestContextHolder： 只要有  contextHolder结尾的底层都是使用threadLocal线程进行存储数据
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

//        获取被增强对象上的注解对象
        SystemLog systemLog=getSystemLog(pj);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURI());
        // 打印描述信息
        log.info("BusinessName   : {}",systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}",pj.getSignature().getDeclaringType(),((MethodSignature) pj.getSignature()).getName() );
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(pj.getArgs()) );

    }

    private SystemLog getSystemLog(ProceedingJoinPoint pj) {
        MethodSignature signature =(MethodSignature) pj.getSignature();
        SystemLog annotation = signature.getMethod().getAnnotation(SystemLog.class);
        return annotation;
    }

    private void handlerAfter(Object res) {
        // 打印出参
        log.info("Response       : {}",JSON.toJSONString(res) );
    }
}
