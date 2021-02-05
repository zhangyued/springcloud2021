package com.zy.springcloud.Config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    @Bean
    @LoadBalanced //集群情况下需要给出进行负载均衡机制，让服务调用方正确从服务提供方集群中找出一个进行调用
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
