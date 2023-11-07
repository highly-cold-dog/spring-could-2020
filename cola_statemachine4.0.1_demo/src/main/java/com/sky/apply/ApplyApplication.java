package com.sky.apply;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author dlf
 * @date 2022/6/14 18:06
 */
@SpringBootApplication(scanBasePackages = {"com.alibaba.cola","com.sky"})
//@EnableDiscoveryClient
public class ApplyApplication {

    @Value("${server.port}")
    private static String port;

    public static void main(String[] args) {
        SpringApplication.run(ApplyApplication.class, args);
        System.err.println(ApplyApplication.port);
    }
}
