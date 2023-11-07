package com.sky.tech.config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 拦截器统一校验
 *
 * @author dlf
 * @date 2023/5/16 17:27
 */
@Component
public class JwtFilter extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Resource
    private JwtUtil jwtUtil;

    //请求转发到api之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("进入拦截器");
        final String authHeader = request.getHeader("Authorization");
        if (ObjectUtil.isNotEmpty(authHeader)) {

            Claims claims = null;
            try {
                //可能存在过了有效期得情况
                claims =  jwtUtil.pareJWT(authHeader);
            }catch (ExpiredJwtException e){
                response.setContentType("application/json; charset=utf-8");
                response.setStatus(418);
                PrintWriter writer = response.getWriter();
            /*writer.print(JSON(data, SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteDateUseDateFormat));*/
                writer.close();
                response.flushBuffer();
                return false;
            }
            if (ObjectUtil.isNotEmpty(claims)) {
                if ("admin".equals(claims.get("roles"))) {
                    request.setAttribute("admin_claims", claims);
                }
                if ("user".equals(claims.get("roles"))) {
                    request.setAttribute("user_claims", claims);
                }
            }
            return true;
        }else {
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(418);
            PrintWriter writer = response.getWriter();
            /*writer.print(JSON(data, SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteDateUseDateFormat));*/
            writer.close();
            response.flushBuffer();
            return false;
        }
    }
}
