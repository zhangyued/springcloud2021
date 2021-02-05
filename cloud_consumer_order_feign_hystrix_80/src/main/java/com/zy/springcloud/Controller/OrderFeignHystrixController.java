package com.zy.springcloud.Controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.zy.springcloud.Service.PaymentFeignHystrixService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController("OrderFeignHystrixController.v1")
@RequestMapping("/consumer/payment")
@Slf4j
@DefaultProperties(defaultFallback = "paymentGlobalFallback")
public class OrderFeignHystrixController {

    @Resource
    private PaymentFeignHystrixService paymentFeignHystrixService;

    @GetMapping("/hystrix/ok/{id}")
    public String paymentInfo_Ok(@PathVariable("id") Integer id){
        log.info("开始调用远程服务");
        return paymentFeignHystrixService.paymentInfo_Ok(id);
    }

    @GetMapping("/hystrix/timeout/{id}")
    //可以放在Controller上面通过@HystrixCommand注解和@DefaultProperties注解来实现fallback降级，
    // 但是缺点是得给每一个方法上添加对应的注解或者独有的fallback方法
    //为了将fallback和业务解耦，可以将fallback全部放在feign调用的接口处，然后添加对应每一个远程调用服务的fallback方法即可
    //同时因在接口paymentFeignHystrixService上有对应的fallback方法，如果服务提供方正常，则执行该处定义的paymentInfo_TimeOutHandler
    //如果服务提供方不正常比如挂掉了，此处定义的paymentInfo_TimeOutHandler无效，返回的是feign接口定义的fallback内容
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "3000")})
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        log.info("开始调用远程服务");
        return paymentFeignHystrixService.paymentInfo_TimeOut(id);
    }

    /**
     * 专属fallback
     * @param id
     * @return
     */
    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池：" + Thread.currentThread().getName() + "  调用远程服务超时" + id  ;
    }

    @GetMapping("/hystrix/timeout2/{id}")
    @HystrixCommand
    //当在feign调用远程服务接口paymentFeignHystrixService上通过添加fallback的方法的时候，
    // 那么添加@HystrixCommand注解即使之前配合@DefaultProperties注解可以实现全局fallback（若想验证该功能，则把接口paymentFeignHystrixService上的fallback即可），
    //此时也不会去执行全局fallback里面的方法内容，而是执行具体的paymentFeignHystrixService接口里面方法对应的fallback内容
    public String paymentInfo_TimeOut2(@PathVariable("id") Integer id){
        log.info("开始调用远程服务");
        return paymentFeignHystrixService.paymentInfo_TimeOut(id);
    }
    /**
     * 全局fallback方法
     * @return
     */
    public String paymentGlobalFallback(){
        return "服务异常请稍后重试";
    }
}
