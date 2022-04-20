package com.yangjiahai.ebookdownload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/18 11:11
 */
@EnableDiscoveryClient
@SpringBootApplication
public class  GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
