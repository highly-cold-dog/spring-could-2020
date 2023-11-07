package com.sky.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author dlf
 * @date 2022/7/9 0:51
 */
@Service
public class PaymentService {

    public String paymentInfo_Ok(Integer id){
        return "线程池"+Thread.currentThread().getName()+"paymentInfo_Ok,id"+id
                +"\t"+"哈哈哈";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfo_TimeOut(Integer id){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池:"+Thread.currentThread().getName()
                +"id:"+id+
                "\t"+"hahhah";
    }

    /**
     * 用来善后的方法
     */
    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池:"+Thread.currentThread().getName()+"8001系统繁忙或者运行报错，请稍后再试。id:"+id;
    }
}
