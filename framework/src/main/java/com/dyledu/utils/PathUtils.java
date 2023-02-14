package com.dyledu.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PathUtils {
    /**
     * 测试测试.png-->2023/02/14/4c277139fab244a1a642f9ce7fea9d25.png
     * @param fileName
     * @return
     */
    public static String generateFilePath(String fileName){
        //根据日期生成路径   2022/1/15/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String datePath = sdf.format(new Date());
        //uuid作为文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //后缀和文件后缀一致
        int index = fileName.lastIndexOf(".");
        // test.jpg -> .jpg
        String fileType = fileName.substring(index);
        return new StringBuilder().append(datePath).append(uuid).append(fileType).toString();
    }

    /**
     * 测试测试.png--》{path:"2023/02/14/4c277139fab244a1a642f9ce7fea9d25.png",datePath:"2023/02/14"}
     * @param fileName
     * @return
     */
    public static Map generateFilePath2(String fileName){
        //根据日期生成路径   2022/1/15/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String datePath = sdf.format(new Date());
        //uuid作为文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //后缀和文件后缀一致
        int index = fileName.lastIndexOf(".");
        // test.jpg -> .jpg
        String fileType = fileName.substring(index);
        Map<Object, Object> map = new HashMap<>();
        map.put("path",new StringBuilder().append(datePath).append(uuid).append(fileType).toString());
        map.put("datePath",datePath);
        map.put("fileName",new StringBuilder().append(uuid).append(fileType).toString());
        return map;
    }
}
