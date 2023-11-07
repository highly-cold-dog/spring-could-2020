package com.sky.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dlf
 * @date 2022/6/18 16:14
 */
@Configuration
public class MySelfRule {
    @Bean
    public IRule myRule() {
        //采用轮询的方式
        return new RoundRobinRule();
    }
}
