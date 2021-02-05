package com.zy.springcloud.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController("OrderController.v1")
@RequestMapping("/consumer/payment")
@Slf4j
public class OrderController {

    private static final String PAYMENT_URL = "http://CONSUL-PROVIDER-PAYMENT";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consul")
    public String paymentInfo(){
        log.info("开始调用远程查询服务");

        return restTemplate.getForObject(PAYMENT_URL + "/payment/consul",String.class);
    }
}
