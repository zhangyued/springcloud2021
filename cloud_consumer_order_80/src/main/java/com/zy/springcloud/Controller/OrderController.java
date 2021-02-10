package com.zy.springcloud.Controller;

import com.zy.springcloud.Entity.CommonResult;
import com.zy.springcloud.Entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController("OrderController.v1")
@RequestMapping("/consumer/payment")
@Slf4j
public class OrderController {

    //单机版写ip和端口没问题
    private static final String  PAYMENT_CREAT_URL = "http://localhost:8001";
    private static final String  PAYMENT_GET_URL = "http://localhost:8002";

    //集群情况下，只需要提供服务名，注册中心会根据服务名来找到对应的服务
    private static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/create")
    public CommonResult<Payment> create(Payment payment){
        log.info("开始调用远程创建服务");
        //return restTemplate.postForObject(PAYMENT_CREAT_URL+"/payment/create",payment,CommonResult.class);
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/getPaymentById/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        log.info("开始调用远程查询服务");
        //return restTemplate.getForObject(PAYMENT_GET_URL +"/payment/getPaymentById/"+ id,CommonResult.class);
        return restTemplate.getForObject(PAYMENT_URL + "/payment/getPaymentById/" + id,CommonResult.class);
    }


    @GetMapping("/getPaymentById2/{id}")
    public CommonResult<Payment> getPaymentById2(@PathVariable("id") Long id){
        log.info("开始调用远程查询服务");
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/getPaymentById/" + id,CommonResult.class);
        if(entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else{
            return new CommonResult<>(444,"操作失败");
        }
    }

    @GetMapping("/zipkin")
    public String zipkin(){
        return restTemplate.getForObject(PAYMENT_URL + "/payment/zipkin",String.class);
    }
}
