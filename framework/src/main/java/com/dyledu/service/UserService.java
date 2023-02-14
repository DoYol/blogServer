package com.dyledu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyledu.domain.ResponseResult;
import com.dyledu.domain.entity.User;

public interface UserService extends IService<User> {
    ResponseResult blogLogin(User user);

    ResponseResult loginOut(String userID);

    ResponseResult register(User user);

    ResponseResult getUserInfo(String id);

    ResponseResult updateUserInfo(User user);
}
