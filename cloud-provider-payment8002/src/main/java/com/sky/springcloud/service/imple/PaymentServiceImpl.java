package com.sky.springcloud.service.imple;

import com.sky.entyties.Payment;
import com.sky.springcloud.dao.PaymentDao;
import com.sky.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author dlf
 * @date 2022/6/2 17:30
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
