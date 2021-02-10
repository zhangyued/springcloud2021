package com.zy.springcloud.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController("OrderController.v1")
@RequestMapping("/consumer/payment")
@Slf4j
public class OrderController {

    @Value("${service-url.nacos-user-service}")
    private String PAYMENT_URL;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/getPaymentServerPort")
    public String getPaymentServerPort(){
        return restTemplate.getForObject(PAYMENT_URL + "/payment/getPaymentServerPort",String.class);
    }
}
