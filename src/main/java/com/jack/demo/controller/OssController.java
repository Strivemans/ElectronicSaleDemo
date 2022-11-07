package com.jack.demo.controller;

import com.jack.demo.common.R;
import com.jack.demo.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * \* User: jack
 * \* Date: 2022/10/17
 * \* Time: 1:15
 * \* Description: OSS 的 操作控制层
 */
@RequestMapping("/oss")
@RestController
@CrossOrigin
@Api("阿里云存储管理")
public class OssController {
    @Resource
    private OssService ossService;

    @PostMapping("upload")
    public R upload(@ApiParam(name = "file",value = "上传的文件",required = true) MultipartFile file){
        String url = ossService.upload(file);
        return R.ok().data("url",url);
    }
}
