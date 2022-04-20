package com.yangjiahai.ebookdownload.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.yangjiahai.ebookdownload.dto.OssDTO;
import com.yangjiahai.ebookdownload.service.DownloadService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Random;
import java.util.UUID;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/15 12:38
 */
@Slf4j
@Service
public class DownloadServiceImpl implements DownloadService {
    @Resource
    private OssDTO ossDTO;
    private Logger logger = LoggerFactory.getLogger(DownloadServiceImpl.class);

    /**
     * 下载文件
     *
     * @param url
     * @return
     */
    @Override
    public Boolean download(String url) {

        String endpoint = ossDTO.getEndpoint();
        String accessKeyId = ossDTO.getAccessKeyId();
        String accessKeySecret = ossDTO.getAccessKeySecret();

        String bucketName = ossDTO.getBucketName();
        String[] split = url.split("\\.");
        String s = split[split.length - 2] + "." + split[split.length - 1];
        String objectName = s.substring(4);
        System.out.println("objectName = " + objectName);
        String extension = url.substring(url.lastIndexOf("."));
        System.out.println("extension = " + extension);
        String pathName = "/" + UUID.randomUUID().toString().substring(0, 10) + extension;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(pathName));

        ossClient.shutdown();

        return true;
    }
}
