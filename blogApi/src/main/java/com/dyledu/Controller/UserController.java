package com.dyledu.Controller;

import com.dyledu.annotation.SystemLog;
import com.dyledu.domain.ResponseResult;
import com.dyledu.domain.entity.User;
import com.dyledu.service.UserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping ("{id}")
    @RequiresRoles(value = {"admin","user","link"},logical = Logical.OR)
    @SystemLog(businessName = "获取用户信息")
    public ResponseResult getUserInfo(@PathVariable String id){
        return userService.getUserInfo(id);
    }

    @PostMapping("/register")
    @SystemLog(businessName = "用户注册")
    public ResponseResult registerUser(@RequestBody User user){
          return  userService.register(user);
    }

    @PutMapping("/editInfo")
    @RequiresRoles(value = {"admin","user","link"},logical = Logical.OR)
    @SystemLog(businessName = "修改用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }
}
