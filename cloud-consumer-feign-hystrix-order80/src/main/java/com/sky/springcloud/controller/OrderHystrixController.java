package com.sky.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sky.springcloud.service.PaymentHystrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author dlf
 * @date 2022/7/13 23:41
 */
@RestController
@Slf4j
//@DefaultProperties(defaultFallback = "payment_Global_FallBackMethod")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    //用全局的fallBack方法
    @HystrixCommand
    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") String id) {
        String result = paymentHystrixService.paymentIfo_OK(id);
        return result;
    }

   /* @HystrixCommand(fallbackMethod = "paymentTimeOutFallBackMethod",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
    })*/
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") String id) {
        String result = paymentHystrixService.paymentInfo_TimeOut(id);
        return result;
    }

    /**
     * 善后方法
     */
    public String paymentTimeOutFallBackMethod(@PathVariable("id") String id){
        return "我是消费者80,对方支付系统繁忙请10秒钟后再试或者自己运行出错检查自己!";
    }

    //全局的fallback方法
    public String payment_Global_FallBackMethod(){
        return "Global异常处理信息,请稍后再试!";
    }
}
