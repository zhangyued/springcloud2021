package com.zy.springcloud.Service.Impl;

import com.zy.springcloud.Service.PaymentFeignHystrixService;
import org.springframework.stereotype.Component;

@Component
public class PaymentFeignHystrixFallbackServiceImpl implements PaymentFeignHystrixService {
    @Override
    public String paymentInfo_Ok(Integer id) {
        return "----paymentInfo_Ok调用远程服务异常";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "----paymentInfo_TimeOut调用远程服务异常";
    }
}
