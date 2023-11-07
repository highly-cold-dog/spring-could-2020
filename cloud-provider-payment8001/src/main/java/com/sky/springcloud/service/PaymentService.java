package com.sky.springcloud.service;

import com.sky.entyties.Payment;


/**
 * @author dlf
 * @date 2022/5/29 14:00
 */
public interface PaymentService {

    public int create(Payment payment);

    public Payment getPaymentById(Long id);
}
