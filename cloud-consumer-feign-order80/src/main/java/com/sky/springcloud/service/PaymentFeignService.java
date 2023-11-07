package com.sky.springcloud.service;

import com.sky.entyties.CommonResult;
import com.sky.entyties.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author dlf
 * @date 2022/7/7 14:44
 */
@Component
@FeignClient(value = "could-payment-service")
public interface PaymentFeignService {

    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);

    @GetMapping("/timeOut")
    public String timeOut();

}
