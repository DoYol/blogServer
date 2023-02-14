package com.dyledu.service.Impl;

import com.dyledu.domain.ResponseResult;
import com.dyledu.enums.AppHttpCodeEnum;
import com.dyledu.exception.SystemException;
import com.dyledu.service.UploadService;
import com.dyledu.utils.PathUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
@Data
@ConfigurationProperties(prefix = "oss" )
public class UploadServiceImpl implements UploadService {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String basePath;

    @Override
    public ResponseResult uploadFile(MultipartFile file) {
//        判断图片是否存在
        if(file == null){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOTNULL);
        }
//        判断图片格式是否正确
        if(!file.getOriginalFilename().endsWith(".jpg")&&!file.getOriginalFilename().endsWith(".png")){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
//        生成路径
//        String filePath = PathUtils.generateFilePath(file.getOriginalFilename());
        Map pathMap = PathUtils.generateFilePath2(file.getOriginalFilename());

        //        上传文件到OSS
//        String uploadOss = uploadOss(file,filePath);

//      上传图片到服务器
        File dir = new File(basePath+pathMap.get("datePath"));
        if (!dir.exists()) {
//            如果目录不存在则创建
            dir.mkdir();
        }
        try {
            file.transferTo(new File(basePath+pathMap.get("path")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseResult.okResult(pathMap.get("path"));
    }

    /**
     * 由于上传oss需要域名，没有域名所以暂不上传到七牛云oss上，直接上传到云服务器本地
     * @param imgFile
     * @param filePath
     * @return
     */
    private String uploadOss(MultipartFile imgFile,String filePath){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
//        String accessKey = "PjBoF-nla7ahmmvYgtr8Rjyduy8knOidMiiTZCD0";
//        String secretKey = "3iq_XBdV1YdfJE867I8s-TdtKhKEmcNcNwxW4M29";
//        String bucket = "blog-images5";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;
        try {
            InputStream inputStream=imgFile.getInputStream();

            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果

                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return "http://rhw71v1v6.hn-bkt.clouddn.com/"+key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return null;
    }
}
