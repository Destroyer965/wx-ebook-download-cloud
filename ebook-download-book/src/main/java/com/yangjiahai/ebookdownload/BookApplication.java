package com.yangjiahai.ebookdownload;

import org.apache.catalina.connector.Connector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/18 18:44
 */
@EnableFeignClients
@MapperScan("com.yangjiahai.ebookdownload.mapper")
@SpringBootApplication
@EnableDiscoveryClient
public class BookApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);

    }

}

