package com.zy.springcloud.Controller;

import com.zy.springcloud.Entity.CommonResult;
import com.zy.springcloud.Entity.Payment;
import com.zy.springcloud.Service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("OrderFeignController.v1")
@RequestMapping("/consumer/payment")
@Slf4j
public class OrderFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("/feign/getPaymentById/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        log.info("开始调用远程查询服务");
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping("/feign/timeout")
    public String paymentFeignTimeOut(){
        return paymentFeignService.paymentFeignTimeOut();
    }


}
