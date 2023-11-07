package com.sky.springcloud.service;

import com.sky.entyties.Payment;

/**
 * @author dlf
 * @date 2022/6/2 17:30
 */
public interface PaymentService {
    public int create(Payment payment);

    public Payment getPaymentById(Long id);
}
