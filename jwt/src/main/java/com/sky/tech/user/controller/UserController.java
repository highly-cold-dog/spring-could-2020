package com.sky.tech.user.controller;

import cn.hutool.json.JSONObject;
import com.sky.tech.config.JwtUtil;
import com.sky.tech.user.entity.BaseRequest;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dlf
 * @date 2023/5/17 11:11
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Resource
    JwtUtil jwtUtil;


    @PostMapping("/login")
    public JSONObject login(BaseRequest baseRequest) {
        //生成token
        String token = jwtUtil.createJWT("1", baseRequest.getUserName(), "admin");
        JSONObject result = new JSONObject();
        result.put("token", token);
        result.put("name", baseRequest.getUserName());
        return result;
    }

    @GetMapping("/getInfo")
    public JSONObject getInfo(HttpServletRequest request, HttpServletResponse response) {
        final String authHeader = request.getHeader("Authorization");
        final String token = authHeader.substring(7);
        Claims claims = jwtUtil.pareJWT(token);
        System.out.println("解析之后:" + claims.getAudience());
        return new JSONObject().put("roles", claims.get("roles"));
    }

}
