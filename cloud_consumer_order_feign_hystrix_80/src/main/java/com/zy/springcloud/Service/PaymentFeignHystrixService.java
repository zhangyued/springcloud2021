package com.zy.springcloud.Service;

import com.zy.springcloud.Service.Impl.PaymentFeignHystrixFallbackServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "HYSTRIX-PAYMENT-SERVICE",fallback = PaymentFeignHystrixFallbackServiceImpl.class)
public interface PaymentFeignHystrixService {
    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_Ok(@PathVariable("id") Integer id);


    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id);
}
