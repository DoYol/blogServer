package com.dyledu.service;

import com.dyledu.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    ResponseResult uploadFile(MultipartFile file);
}
