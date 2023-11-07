package com.sky.springcloud.event;

import com.sky.springcloud.model.UserBean;
import lombok.Data;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用户注册事件发布
 *
 * @author dlf
 * @date 2022/10/14 15:39
 */
@Getter
public class UserRegisterEvent extends ApplicationEvent {

    /**
     * 注册对象
     */
    private final UserBean userBean;


    /**
     * 重写构造函数
     * @param source 发生事件的对象
     * @param userBean  注册用户对象
     */
    public UserRegisterEvent(Object source, UserBean userBean) {
        super(source);
        this.userBean = userBean;
    }
}
