package com.dualwings.apisystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({"com.dualwings.apisystem.*"})
@MapperScan({"com.dualwings.apisystem.dao","com.dualwings.apisystem.dto","com.dualwings.apisystem.utils","com.dualwings.apisystem.config"})
//@EnableFeignClients
//@NacosPropertySource(dataId = "boot-user-service", autoRefreshed = true)
//@EnableDiscoveryClient
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}
