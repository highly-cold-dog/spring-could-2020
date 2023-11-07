package com.sky.springcloud.websocket;

import cn.hutool.core.util.ObjectUtil;
import com.sky.springcloud.service.PaymentService;
import com.sky.springcloud.util.IdLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dlf
 */

@Slf4j
@ServerEndpoint("/websocket2/{authorization}")
@Component
public class WebSocketServer {


    //记录当前在线连接数量
    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);


    //存放所有在线的客户端
    private static final Map<String, Session> CLIENTS = new ConcurrentHashMap<>();


    //线程安全的Set用来存放每个客户端对应的myWebSocket对象 根据userId来获取对应的webSocket
    private static ConcurrentHashMap<String, WebSocketServer> webSocketSet = new ConcurrentHashMap<>();


    //存放最近信息
    private static final Queue<String> MESSAGE = new LinkedList<>();


    //最近信息容量
    private static final int ALARM_CAPACITY = 1;

    private static  PaymentService  staticPaymentService;
    //解决WebSocket注入为空的问题，原因Spring容器是一次性注入的
    @Autowired
    public void initService(PaymentService paymentService){
        WebSocketServer.staticPaymentService = paymentService;
    }



    //连接建立成功调用的方法
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "authorization") String authorization) throws Exception {
        if (ObjectUtil.isEmpty(authorization)) {
            throw new Exception("authorization不存在!");
        }
        //在线数+1
        ONLINE_COUNT.incrementAndGet();
        CLIENTS.put(session.getId(), session);
        sendMessage(session.getId(),"您已成功过建立连接，当前分配sessionId为:"+session.getId());
        log.info("有新连接加入: {},当前在线人数为: {}", session.getId(), ONLINE_COUNT.get());
    }


    //关闭连接调用的方法
    @OnClose
    public void onClose(Session session) {
        //在线数减一
        ONLINE_COUNT.decrementAndGet();
        CLIENTS.remove(session.getId());
        log.info("有一连接关闭:{},当前连接人数为:{}", session.getId(), ONLINE_COUNT.get());

    }

    //收到客户端消息后调用的方法
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自,{} 的消息，开始做自己的业务代码", session.getId());
    }

    @OnError
    public void onError(Session session, Throwable err) {
        log.error("发生错误" + (null == session ? "" : "客户端id为" + session.getId()));
        err.printStackTrace();
    }

    //发送消息给指定客户端
    public static void sendMessage(String sessionId, String message) {
        if (ObjectUtil.isEmpty(sessionId) || ObjectUtil.isEmpty(message)) {
            return;
        }
        synchronized (IdLockUtil.getLock("WebSocket:send" + sessionId)) {
            log.info("服务端给客户端[{}]发送消息", sessionId);
            try {
                Session session = CLIENTS.get(sessionId);
                if (ObjectUtil.isNotNull(session)) {
                    session.getBasicRemote().sendText(message);
                }
            } catch (Exception e) {
                log.error("服务端发送消息给客户端[{}]失败:{}", sessionId, e);
            }
        }
    }


    // 发送消息给全部的客户端
    public static void sendMessageToAll(String message) throws IOException {
        //群发消息
        CLIENTS.forEach((sessionId, client) -> sendMessage(sessionId, message));
    }


    /**
     * 获取当前在线的所有设备
     */
    public static Map<String, Session> getOnlineClients(){
        return WebSocketServer.CLIENTS;
    }
}
