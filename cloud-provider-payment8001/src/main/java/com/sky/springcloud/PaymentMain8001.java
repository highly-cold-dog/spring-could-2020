package com.sky.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author dlf
 * @date 2022/5/29 11:03
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
//开启AOP
@EnableAspectJAutoProxy
public class PaymentMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class, args);
        System.err.println("PaymentMain8001----start---!");
    }
}
