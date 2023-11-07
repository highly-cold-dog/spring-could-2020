package com.sky.tech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dlf
 * @date 2023/5/16 11:06
 */
@SpringBootApplication(scanBasePackages = {"com.alibaba.cola", "com.sky"})
public class JwtApplication {
    public static void main(String[] args) {
        SpringApplication.run(JwtApplication.class, args);
        System.err.println("jwt-auth-start-success!");
    }
}
