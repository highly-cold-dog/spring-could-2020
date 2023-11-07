package com.sky.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * websocket相关配置
 *
 * @author dlf
 * @date 2022/7/29 17:42
 */
@Configuration
public class WebSocketConfig {

    /**
     * 这个bean的注册，用于扫描带有@ServerEndpoint的注解成为websocket，如果你使用外置的tomcat就不需要该
     * 配置文件
     */
    @Bean
    public ServerEndpointExporter serverEndpointExport() {
        return new ServerEndpointExporter();
    }
}
