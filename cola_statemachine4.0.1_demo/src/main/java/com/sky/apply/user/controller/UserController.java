package com.sky.apply.user.controller;

import com.sky.apply.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author dlf
 * @date 2023/4/21 15:40
 */
@RestController
@Slf4j
@RequestMapping("/test")
public class UserController {

    @Value("${server.port}")
    private String serverPort;


    @Resource
    public UserService userService;

    @GetMapping("/testMachine")
    public String testMachine() {
        return userService.actionSys();
    }

}
