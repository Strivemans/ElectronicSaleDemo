package com.jack.demo.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * OSS服务层
 */
public interface OssService {
    String upload(MultipartFile file);
}
