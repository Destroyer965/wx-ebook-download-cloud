package com.yangjiahai.ebookdownload.dto;


import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/7 20:24
 */
@Data
@Accessors(chain = true)
@Component
@ConfigurationProperties(prefix = "oss")
public class OssDTO {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
