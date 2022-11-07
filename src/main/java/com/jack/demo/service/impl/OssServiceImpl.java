package com.jack.demo.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.jack.demo.service.OssService;
import com.jack.demo.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * \* User: jack
 * \* Date: 2022/10/17
 * \* Time: 1:09
 * \* Description:OSS实现类
 */
@Service
public class OssServiceImpl implements OssService {

    /**
     * 文件上传到OSS
     * @param file ： 文件二进制
     * @return ： 存储的URL
     */
    @Override
    public String upload(MultipartFile file) {
        String url=null;
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();

            //1.随机唯一值 防止文件名重复
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName = uuid+fileName;

            //2.通过时间格式创建文件路径 保存文件
            String datapath = new DateTime().toString("yyyy/MM/dd");
            fileName = datapath+"/"+fileName;

            // 创建PutObject请求。
            ossClient.putObject(bucketName, fileName, inputStream);
            url = "http://"+bucketName+"."+endpoint+"/"+fileName;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        return url;
    }
}
