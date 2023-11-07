package com.sky.springcloud.controller;

import com.sky.entyties.CommonResult;
import com.sky.entyties.Payment;
import com.sky.springcloud.service.PaymentFeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author dlf
 * @date 2022/7/7 14:48
 */
@RestController
public class OrderFeignController {
    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping("/consumer/payment/timeOut")
    public String FeignTimeOut(){
        return paymentFeignService.timeOut();
    }
}
