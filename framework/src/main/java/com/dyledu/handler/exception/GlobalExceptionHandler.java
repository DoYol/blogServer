package com.dyledu.handler.exception;

import com.dyledu.domain.ResponseResult;
import com.dyledu.enums.AppHttpCodeEnum;
import com.dyledu.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//全局异常处理
//请求先经过过滤器，所以spring security的认证授权异常处理器先捕获，等到请求被springmvc接管了，出现异常就是这个全局异常处理器捕获
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        log.info("出现了异常！ {}",e);
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        log.info("系统出现了异常！ {}",e);
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,e.getMessage());
    }

    @ExceptionHandler(AccountException.class)
    public ResponseResult AccountExceptionHandler(AccountException e){
        log.info("登陆出现问题:",e);
        return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR, e.getMessage());
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseResult ShiroUnauthenticatedExceptionHandler(UnauthenticatedException e){
        log.info("[TOKEN] token失效 Unauthorized");
        return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN, "[TOKEN] token失效 Unauthorized");
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseResult ShiroUnauthorizedExceptionHandler(UnauthorizedException e){
        return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
    }
}
