package com.sky.tect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dlf
 * @date 2023/5/18 15:50
 */
@SpringBootApplication(scanBasePackages = {"com.alibaba.cola", "com.sky"})
public class SpringSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
        System.err.println("SpringSecurityApplication-start-success!");
    }
}
