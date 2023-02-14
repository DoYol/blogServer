package com.dyledu.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils(){}

//    copy单个对象
    public static <V> V copyBean(Object source,Class<V> clazz){
       V result=null;
        try {
            result=clazz.newInstance();
            BeanUtils.copyProperties(source,result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
//  copy List集合
    public static <T> List<T> copyBeanList(List<?> list,Class<T> clazz){
        return list.stream().map(item ->
            copyBean(item, clazz)
        ).collect(Collectors.toList());
    }

}
