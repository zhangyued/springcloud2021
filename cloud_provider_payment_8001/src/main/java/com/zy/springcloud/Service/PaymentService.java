package com.zy.springcloud.Service;

import com.zy.springcloud.Entity.Payment;

public interface PaymentService {
    public int create(Payment payment);

    public Payment getPaymentById(Long id);
}
