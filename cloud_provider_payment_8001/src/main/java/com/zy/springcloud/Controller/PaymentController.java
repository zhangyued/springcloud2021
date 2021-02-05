package com.zy.springcloud.Controller;


import com.zy.springcloud.Entity.CommonResult;
import com.zy.springcloud.Entity.Payment;
import com.zy.springcloud.Service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController("PaymentController.v1")
@RequestMapping("/payment")
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("插入结果：" + result);
        if (result > 0){
            return new CommonResult(200,"插入数据库成功,调用服务端口号为：" + serverPort,result);
        }else{
            return new CommonResult(444,"插入数据库失败,调用服务端口号为：" + serverPort,null);
        }
    }


    @GetMapping("/getPaymentById/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("查到结果：" + payment);
        if (payment != null){
            return new CommonResult(200,"查询成功，调用服务端口号为：" + serverPort,payment);
        }else{
            return new CommonResult(444,"查询不到记录，id为：" + id + "调用服务端口号为：" + serverPort,null);
        }
    }
    /**
     * 服务发现:对外暴露自身服务信息
     */
    @GetMapping("/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String service:services) {
            log.info("element:" + service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance:instances) {
            log.info("ServiceId:" + instance.getServiceId() + ",Host:" + instance.getHost() + ",Port:" + instance.getPort() + ",Uri:" + instance.getUri());
        }
        return this.discoveryClient;
    }

    /**
     *
     * 测试openfeign的超时控制
     */
    @GetMapping("/feign/timeout")
    public String paymentFeignTimeOut(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}
