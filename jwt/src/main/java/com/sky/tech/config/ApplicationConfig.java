package com.sky.tech.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

/**
 * 配置拦截器检验路径
 * @author dlf
 * @date 2023/5/16 17:56
 */
@Configuration
public class ApplicationConfig extends WebMvcConfigurationSupport {


    @Resource
    private JwtFilter jwtFilter;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtFilter).
                addPathPatterns("/**").
                excludePathPatterns("/**/login");
    }
}
