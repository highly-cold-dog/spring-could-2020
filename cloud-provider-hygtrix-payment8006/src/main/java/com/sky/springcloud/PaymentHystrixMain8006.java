package com.sky.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author dlf
 * @date 2022/7/9 0:49
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class PaymentHystrixMain8006 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentHystrixMain8006.class,args);
        System.err.println("PaymentHystrixMain8006====start======success!");
    }
}
