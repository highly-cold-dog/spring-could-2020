package com.sky.springcloud.controller;


import com.sky.entyties.CommonResult;
import com.sky.entyties.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author dlf
 * @date 2022/5/29 17:07
 */
@RestController
@Slf4j
public class OrderController {

    //单机版可以这样写，但是集群之后需要使用配置中心注册的服务名。
    public static final String PAYMENT_URL = "http://localhost:8001";
    //eureka上面注册的服务名称
    public static final String EUREKA_PAYMENT_URL = "http://COULD-PAYMENT-SERVICE";
    @Resource
    private RestTemplate restTemplate;


    @PostMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(EUREKA_PAYMENT_URL+"/createPayment",payment,CommonResult.class);
    }

    @GetMapping("consumer/getPayment/{id}")
    public CommonResult<Payment> getPayment(@PathVariable Long id)
    {
        return restTemplate.getForObject(EUREKA_PAYMENT_URL+"/get/"+id,CommonResult.class);
    }
}
