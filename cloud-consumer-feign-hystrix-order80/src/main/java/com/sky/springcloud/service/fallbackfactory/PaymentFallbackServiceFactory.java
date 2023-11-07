package com.sky.springcloud.service.fallbackfactory;

import com.sky.springcloud.service.PaymentHystrixService;
import org.springframework.stereotype.Component;

/**
 * @author dlf
 * @date 2022/7/18 23:30
 */
@Component
public class PaymentFallbackServiceFactory implements PaymentHystrixService {
    @Override
    public String paymentIfo_OK(String id) {
        return "=======paymentFallBackService paymentIfo_OK== ";
    }

    @Override
    public String paymentInfo_TimeOut(String id) {
        return "paymentFallBackService paymentInfo_TimeOut =====";
    }
}
