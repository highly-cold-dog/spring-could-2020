package com.sky.springcloud.controller;

import com.sky.springcloud.websocket.WebSocketServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 测试WebSocket发送消息
 *
 * @author dlf
 * @date 2022/9/16 11:25
 */
@RestController
@RequestMapping("/WebSocketTest")
public class WebSocketController {

    @Resource
    private WebSocketServer webSocketServer;

    @RequestMapping("/send")
    public String sendMessageTest() throws IOException {
        WebSocketServer.sendMessageToAll("雷猴啊！");
        return "success!";
    }
}
