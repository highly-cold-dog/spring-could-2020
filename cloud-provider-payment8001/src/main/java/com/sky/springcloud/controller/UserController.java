package com.sky.springcloud.controller;

import com.sky.springcloud.annotation.LogApi;
import com.sky.springcloud.model.UserBean;
import com.sky.springcloud.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试AOP相关
 *
 * @author dlf
 * @date 2022/10/14 15:11
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/register")
    @LogApi
    public String register(UserBean userBean) {
        userService.register(userBean);
        return "注册成功!";
    }
}
