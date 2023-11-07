package com.sky.tech.controller;

import com.sky.tech.enty.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dlf
 * @date 2023/5/29 17:32
 */
@RestController
public class TestController {

    @GetMapping("/user/{name}")
    public User user(@PathVariable String name){
        return new User(name, 20);
    }
}
