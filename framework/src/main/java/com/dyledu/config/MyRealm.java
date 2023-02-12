package com.dyledu.config;

import com.alibaba.fastjson.JSON;
import com.dyledu.constans.SystemConstants;
import com.dyledu.domain.ResponseResult;
import com.dyledu.enums.AppHttpCodeEnum;
import com.dyledu.mapper.RoleMapper;
import com.dyledu.utils.JwtToken;
import com.dyledu.utils.JwtUtil;
import com.dyledu.utils.RedisCache;
import com.dyledu.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当检测用户需要权限或者需要判定角色的时候才会走
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("MyRealm doGetAuthorizationInfo() 方法授权 ");
        String token = principalCollection.toString();
        String userToken = null;
        try {
            userToken = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            throw new UnauthenticatedException("tokenError");
        }
        //查询用户的角色
        Long userId = Long.valueOf(userToken);
        String userRole = roleMapper.selectRoleByUserID(userId);
        //查询用户的权限

        //将当前用户的角色 和 权限 添加到该对象中 并返回
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole(userRole);
        return info;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证, 如果没有权限注解的话就不会去走上面的方法只会走这个方法
     * 其实就是 过滤器传过来的token 然后进行 验证 authenticationToken.toString() 获取的就是
     * 你的token字符串,然后你在里面做逻辑验证就好了,没通过的话直接抛出异常就可以了
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证-----------》》》》");
        String jwt = (String) authenticationToken.getCredentials();
        String userToken = null;
        try {
            userToken = JwtUtil.parseJWT(jwt);
        } catch (Exception e) {
            throw new UnauthenticatedException("tokenError");
        }
        Object userInfo = redisCache.getCacheObject(SystemConstants.BLOG_TOKEN + userToken);
        log.info(userInfo.toString());
        if (userInfo == null) {
            throw new UnauthenticatedException("tokenError");
        }
        return new SimpleAuthenticationInfo(jwt, jwt, "MyRealm");
    }
}
