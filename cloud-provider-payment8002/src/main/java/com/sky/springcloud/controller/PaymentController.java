package com.sky.springcloud.controller;

import com.sky.entyties.CommonResult;
import com.sky.entyties.Payment;
import com.sky.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author dlf
 * @date 2022/6/2 17:21
 */
@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;
    @Resource
    private PaymentService paymentService;


    @PostMapping("/createPayment")
    public CommonResult createPayment(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("插入结果:" + result);
        if (result > 0) {
            return new CommonResult(200, "插入数据库成功!端口号"+serverPort, null);
        } else {
            return new CommonResult(500, "插入数据库失败!端口号"+serverPort, null);
        }
    }

    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        return new CommonResult(200, "查询成功!端口号"+serverPort, payment);
    }

    @GetMapping("/timeOut")
    public String timeOut() {
        /*try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return "查询成功!端口号" + serverPort;
    }
}
