package com.dyledu.filter;

import com.alibaba.fastjson.JSON;
import com.dyledu.constans.SystemConstants;
import com.dyledu.domain.ResponseResult;
import com.dyledu.domain.entity.User;
import com.dyledu.enums.AppHttpCodeEnum;
import com.dyledu.utils.JwtToken;
import com.dyledu.utils.JwtUtil;
import com.dyledu.utils.RedisCache;
import com.dyledu.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class JwtFilter extends BasicHttpAuthenticationFilter {
    
    

    
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        log.info(" // 判断请求的请求头上是否带上 \"Token\"");
        // 判断请求的请求头上是否带上 "Token"
        if (((HttpServletRequest) request).getHeader("token") != null) {
            // 如果存储，则进入executeLogin方法执行登录，检查 token 是否正确
            try {
                executeLogin(request, response);
                return true;
            } catch (AuthenticationException e) {
                // token 错误
                // TODO
                log.info("token异常"+e.getMessage());
            }
        }
        // 如果请求头不存在 token，则可能是执行登录操作，或者是游客状态登录，无需检查token，直接返回true
        return true;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        log.info("JwtFilter -----> executeLogin() 方法执行");
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("token");
        JwtToken jwtToken = new JwtToken(token);

        // 提交给realm进行登录，如果错误他会抛出一行并捕获。

        log.info("-----执行登陆开始-----");
        getSubject(request, response).login(jwtToken);
        log.info("-----执行登陆结束-----");

        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }


}
