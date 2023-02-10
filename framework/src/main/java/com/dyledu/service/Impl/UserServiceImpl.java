package com.dyledu.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyledu.constans.SystemConstants;
import com.dyledu.domain.ResponseResult;
import com.dyledu.domain.entity.User;
import com.dyledu.domain.vo.BlogLoginUserVo;
import com.dyledu.domain.vo.UserInfoVo;
import com.dyledu.mapper.UserMapper;
import com.dyledu.service.UserService;
import com.dyledu.utils.BeanCopyUtils;
import com.dyledu.utils.JwtUtil;
import com.dyledu.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisCache redisCache;

    /**
     * 博客登录成功，获取用户信息，生成token，返回给前端
     * @param user
     * @return
     */
    @Override
    public ResponseResult blogLogin(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,user.getUserName());
        User userInfo = this.getOne(queryWrapper);
        log.info(userInfo.getPassword());
        log.info(new Md5Hash(user.getPassword(),"yunLong",3).toString());
        if(!userInfo.getPassword().equals(new Md5Hash(user.getPassword(),"yunLong",3).toString())){
            throw new RuntimeException("用户名或密码错误");
        }
        String userId = userInfo.getId().toString();

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(userInfo, UserInfoVo.class);
//        获取token
        String token = JwtUtil.sign(userId);
//        将用户id 和 用户信息存入redis
        redisCache.setCacheObject(SystemConstants.BLOG_TOKEN+userId,userInfo);

        BlogLoginUserVo blogLoginUserVo = new BlogLoginUserVo(token, userInfoVo);
        return ResponseResult.okResult(blogLoginUserVo);
    }
}
