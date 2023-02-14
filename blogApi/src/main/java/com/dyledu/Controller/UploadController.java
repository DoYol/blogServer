package com.dyledu.Controller;

import com.dyledu.annotation.SystemLog;
import com.dyledu.domain.ResponseResult;
import com.dyledu.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

// 文件上传
@Slf4j
@RestController
public class UploadController {
    @Value("${oss.basePath}")
    private String basePath;
    @Autowired
    private UploadService uploadService;
    @PostMapping(value = "/upload",headers = "content-type=multipart/form-data")
    @RequiresRoles(value = {"admin","user","link"},logical = Logical.OR)
    @SystemLog(businessName = "上传图片")
    public ResponseResult uploadFile(@RequestPart MultipartFile file){
           return uploadService.uploadFile(file);
    }

    @GetMapping("/download")
    @RequiresRoles(value = {"admin","user","link"},logical = Logical.OR)
    @SystemLog(businessName = "下载图片")
    public void download(String name, HttpServletResponse response){

        try {
            //        1.输入流。通过文件输入流，读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            //        2.输出流。通过输出流，将文件写回浏览器，就可以在浏览器中看到了
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
//            关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
