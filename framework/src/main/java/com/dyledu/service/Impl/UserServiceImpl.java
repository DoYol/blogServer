package com.dyledu.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyledu.constans.SystemConstants;
import com.dyledu.domain.ResponseResult;
import com.dyledu.domain.entity.User;
import com.dyledu.domain.vo.BlogLoginUserVo;
import com.dyledu.domain.vo.UserInfoVo;
import com.dyledu.enums.AppHttpCodeEnum;
import com.dyledu.exception.SystemException;
import com.dyledu.mapper.UserMapper;
import com.dyledu.service.UserService;
import com.dyledu.utils.BeanCopyUtils;
import com.dyledu.utils.JwtUtil;
import com.dyledu.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Value("${shiroSalt}")
    String shiroSalt;

    @Value("${shiroIterations}")
    Integer shiroIterations;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserMapper userMapper;

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
        log.info(shiroSalt);
        log.info(shiroIterations.toString());
        if(!userInfo.getPassword().equals(new Md5Hash(user.getPassword(),shiroSalt,shiroIterations).toString())){
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



    /**
     * 博客用户退出登陆
     * @return
     */
    @Override
    public ResponseResult loginOut(String userID) {
        redisCache.deleteObject(SystemConstants.BLOG_TOKEN+userID);
        return ResponseResult.okResult();
    }


    /**
     * 获取用户信息
     * @param id
     * @return
     */
    @Override
    public ResponseResult getUserInfo(String id) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId,Long.valueOf(id));
        User user = this.getOne(wrapper);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @Override
    public ResponseResult updateUserInfo(User user) {
        this.updateById(user);
        return ResponseResult.okResult();
    }


    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public ResponseResult register(User user) {
//        判空
        if(user.getUserName()==null||user.getUserName().equals("")){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
//        判断用户名和昵称是否重复
        if(userNameExit(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExit(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
//        密码加密
        user.setPassword(new Md5Hash(user.getPassword(),shiroSalt,shiroIterations).toString());
        log.info(user.toString());
        this.save(user);
        return ResponseResult.okResult();
    }



    private Boolean userNameExit(String userName){
        Long id = userMapper.selectIdByUserName(userName);
        if(id!=null){
            return true;
        }
        return false;
    }

    private Boolean nickNameExit(String nickName){
        LambdaQueryWrapper<User> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(User::getNickName,nickName);
        User user = this.getOne(queryWrapper1);
        if(user!=null){
            return true;
        }
        return false;
    }
}
