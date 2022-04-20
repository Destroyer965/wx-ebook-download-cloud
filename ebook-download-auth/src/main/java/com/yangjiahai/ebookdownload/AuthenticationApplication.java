package com.yangjiahai.ebookdownload;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/17 19:13
 */
@MapperScan(basePackages = "com.yangjiahai.ebookdownload.mapper")
@SpringBootApplication
@EnableDiscoveryClient
public class AuthenticationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class,args);
    }
}
