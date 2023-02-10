package com.dyledu.Controller;

import com.dyledu.annotation.SystemLog;
import com.dyledu.domain.ResponseResult;
import com.dyledu.domain.entity.User;
import com.dyledu.enums.AppHttpCodeEnum;
import com.dyledu.exception.SystemException;
import com.dyledu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class BlogLoginController {

    @Autowired
    private UserService userService;
    @PostMapping("/login")
    @SystemLog(businessName = "博客登录")
    public ResponseResult BlogLogin(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
           throw  new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
       return userService.blogLogin(user);
    }
}
