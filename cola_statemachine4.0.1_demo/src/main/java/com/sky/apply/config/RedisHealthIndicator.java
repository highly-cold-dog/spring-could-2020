package com.sky.apply.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Configuration;

/**
 * 重写redis的健康检查
 *
 * @author dlf
 * @date 2023/4/21 17:20
 */
@Configuration
public class RedisHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        return Health.up().build();
    }
}
