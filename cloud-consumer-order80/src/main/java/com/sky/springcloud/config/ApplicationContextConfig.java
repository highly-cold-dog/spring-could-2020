package com.sky.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author dlf
 * @date 2022/5/29 17:09
 */
@Configuration
public class ApplicationContextConfig {



    @Bean
    //默认的负载均衡机制
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
