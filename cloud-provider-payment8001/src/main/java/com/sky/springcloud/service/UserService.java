package com.sky.springcloud.service;

import com.sky.springcloud.model.UserBean;

/**
 * 测试AOP 事件监听与发布
 *
 * @author dlf
 * @date 2022/10/14 15:13
 */
public interface UserService {

    String register(UserBean userBean);
}
