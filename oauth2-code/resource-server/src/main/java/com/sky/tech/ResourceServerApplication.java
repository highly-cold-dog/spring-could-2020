package com.sky.tech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dlf
 * @date 2023/5/29 17:40
 */
@SpringBootApplication
public class ResourceServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class, args);
        System.err.println("ResourceServerApplication----start--success!");
    }
}
