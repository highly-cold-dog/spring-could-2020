package com.sky.tech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * oauth 授权校验
 * @author dlf
 * @date 2023/5/29 17:29
 */
@SpringBootApplication(scanBasePackages = {"com.alibaba.cola", "com.sky"})
public class AuthorizationServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
        System.err.println("AuthorizationServerApplication-start-success!");
    }
}
