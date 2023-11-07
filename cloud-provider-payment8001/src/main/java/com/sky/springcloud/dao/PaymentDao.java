package com.sky.springcloud.dao;

import com.sky.entyties.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author dlf
 * @date 2022/6/2 17:29
 */
@Mapper
public interface PaymentDao {
    int create(Payment payment);

    Payment getPaymentById(@Param("id") Long id);


}
