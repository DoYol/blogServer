package com.dyledu.Controller;

import com.dyledu.annotation.SystemLog;
import com.dyledu.domain.ResponseResult;
import com.dyledu.domain.entity.User;
import com.dyledu.enums.AppHttpCodeEnum;
import com.dyledu.exception.SystemException;
import com.dyledu.service.UserService;
import com.dyledu.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
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

    @RequiresRoles(value = {"admin","user","link"},logical = Logical.OR)
    @PostMapping("/loginOut")
//    @SystemLog(businessName = "博客用户退出登陆")
    public ResponseResult LoginOut(ShiroHttpServletRequest request){
        String userID=null;
        try{
            userID = JwtUtil.parseJWT(request.getHeader("token"));
        }catch (Exception e){
            return ResponseResult.okResult();
        }
        return userService.loginOut(userID);
    }
}
