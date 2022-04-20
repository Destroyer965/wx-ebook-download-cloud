package com.yangjiahai.ebookdownload.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import com.yangjiahai.ebookdownload.dto.OssDTO;
import com.yangjiahai.ebookdownload.service.UploadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/7 20:20
 */
@Service
public class UploadServiceImpl implements UploadService {
    @Resource
    private OssDTO uploadDTO;

    /**
     * 文件上传
     * @param in
     * @param moudle
     * @param originName
     * @return
     */
    @Override
    public List<String> upload(List<InputStream> in, String moudle, List<String> originName) {
        String endpoint = uploadDTO.getEndpoint();
        String accessKeyId = uploadDTO.getAccessKeyId();
        String accessKeySecret = uploadDTO.getAccessKeySecret();
        String bucketName = uploadDTO.getBucketName();
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // image/IMG_2530.JPG
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        //文件夹
        String folder = localDate.format(formatter);
        //文件名
        List<String> fileNames = new ArrayList<>();
        for (String s : originName) {
            fileNames.add(
                    //获取原始文件名
//                    s.replace(s.substring(s.lastIndexOf(".")), "")
                     UUID.randomUUID().toString().substring(0, 10)
                    //获取扩展名
                    + s.substring(s.lastIndexOf("."))
            );
        }
        //文件最终名
        List<String> objectNames = new ArrayList<>();
        for (String fileName : fileNames) {
            objectNames.add(moudle+ "/" + folder+"/"+fileName);
        }
        System.out.println(objectNames);
//        String objectName = moudle + "/" + folder + "/" + finalName + fileExtension;
        // 创建PutObject请求。
        for (int i = 0; i < objectNames.size(); i++) {
            ossClient.putObject(bucketName, objectNames.get(i), in.get(i));
        }
        //关闭ossClient
        ossClient.shutdown();
//        https://wx-ebook-download.oss-cn-chengdu.aliyuncs.com/image/IMG_2530.JPG
        List<String> urls = new ArrayList<>();
        for (String objectName : objectNames) {
            urls.add( "https://" + bucketName + "." + endpoint + "/" + objectName);
        }
        return urls;
    }
}
