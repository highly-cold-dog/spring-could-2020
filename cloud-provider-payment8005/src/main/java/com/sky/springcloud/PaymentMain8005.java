package com.sky.springcloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author dlf
 * @date 2022/6/14 18:06
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PaymentMain8005 {

    @Value("${server.port}")
    private static String port;

    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8005.class, args);
        System.err.println("=======PaymentMain8005=====start======success" + port);
    }
}
