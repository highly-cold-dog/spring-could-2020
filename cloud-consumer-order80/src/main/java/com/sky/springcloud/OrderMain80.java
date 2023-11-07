package com.sky.springcloud;

import com.sky.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @author dlf
 * @date 2022/5/29 16:56
 */
@SpringBootApplication
@RibbonClient(name = "could-payment-service",configuration = MySelfRule.class)
public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class, args);
        System.err.println("OrderMain80----start---success!");
    }
}
