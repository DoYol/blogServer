package com.dyledu.utils;

import lombok.NoArgsConstructor;

//定义线程类 存放当前线程用户信息
@NoArgsConstructor
public class UserThreadLocal {
    //    创建一个线程
    private static final ThreadLocal<String> USER_INFO_THREAD_LOCAL = new ThreadLocal<>();


    /**
     * 清除线程信息
     */
    public static void clear() {
        USER_INFO_THREAD_LOCAL.remove();
    }


    /**
     * 存储用户信息
     */
    public static void set(String userId) {
        USER_INFO_THREAD_LOCAL.set(userId);
    }


    /**
     * 获取当前用户信息
     */
    public static String getCurrentUser() {
        return USER_INFO_THREAD_LOCAL.get();
    }

}
