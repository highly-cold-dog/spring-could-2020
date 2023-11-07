/*
package com.sky.springcloud.websocket;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.sky.springcloud.util.IdLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

*/
/**
 * @author dlf
 * @date 2022/8/2 17:18
 *//*

@Slf4j
@ServerEndpoint("/websocket/{authorization}")
public class WebSocketNew {

    */
/**
     * 记录当前在线连接数
     *//*

    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

    */
/**
     * 存放所有在在线的客户端
     *//*

    private static final Map<String, Session> CLIENTS = new ConcurrentHashMap<>();

    */
/**
     * 存放最近的消息
     *//*

    private static final Queue<String> MESSAGES = new LinkedList<>();

    */
/**
     * 最近信息容量
     *//*

    private static final int ALARM_CAPACITY = 1;

    */
/**
     * 连接建立成功调用的方法
     *//*

    @OnOpen
    public void onOpen(Session session, @PathVariable("authorization") String authorization) throws Exception {
        if (ObjectUtil.isEmpty(authorization)){
            throw  new Exception("authorization is null!");
        }
        ONLINE_COUNT.incrementAndGet();
        CLIENTS.put(session.getId(),session);
        log.info("有新连接加入:{},当前在线人数为:{}",session.getId(),ONLINE_COUNT.get());
    }

    */
/**
     * 连接关闭调用的方法
     *//*

    @OnClose
    public void onClose(Session session){
        //在线数减1
        ONLINE_COUNT.decrementAndGet();
        CLIENTS.remove(session.getId());
        log.info("有一连接关闭:{},当前在线人数为:{}",session.getId(),ONLINE_COUNT.get());
    }

    */
/**
     * 收到客户端消息后调用的方法
     *//*

    @OnMessage
    public void onMessage(String message,Session session){
        log.info("收到消息{},来自于{}",message,session.getId());
    }

    @OnError
    public void onError(Session session,Throwable err){
        log.error("发生错误"+(null == session ? "" : ",客户端id为"+session.getId()));
        err.printStackTrace();
    }

    */
/**
     * 发送消息给知道客户端
     *//*

    public static void sendMessage(String sessionId,String message){
        if (sessionId == null || message == null){
            return;
        }
        synchronized (IdLockUtil.getLock("webSocket:send"+sessionId)){
            log.info("服务端给客户端[{}]发送消息",sessionId);
            try{
                Session session = CLIENTS.get(sessionId);
                if (ObjectUtil.isNotEmpty(session)){
                    session.getBasicRemote().sendText(message);
                }
            }catch (Exception e){
                log.error("服务端发送消息给客户端[{}]失败",sessionId,e);
            }
        }
    }

    public static void sendMessage(String sessionId,Object message){
        sendMessage(sessionId, JSONUtil.toJsonStr(message));
    }

    */
/**
     * 发送消息给全部客户端
     *//*

    public static void sendMessageToAll(String message){
        //群发消息
        CLIENTS.forEach((WebSocketNew::sendMessage));
    }
}
*/
