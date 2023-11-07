package com.sky.springcloud.listener;

import com.sky.springcloud.event.UserRegisterEvent;
import com.sky.springcloud.model.UserBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 用户注册的监听
 * @author dlf
 * @date 2022/10/14 17:46
 */
@Component
public class RegisterUserListener {

    private static final Logger log = LoggerFactory.getLogger(RegisterUserListener.class);

    /**
     * 注册监听实现方法
     * 只需要让监听类被Spring管理即可，@EventListener注解会根据方法内置的时间完成监听
     * @param userRegisterEvent
     */
    @EventListener
    public void register(UserRegisterEvent userRegisterEvent){
        //获取注册对象
        UserBean user = userRegisterEvent.getUserBean();
        log.info("获取到注册对象为:"+user.getUserName()+"===="+user.getPassWord());
    }
}
