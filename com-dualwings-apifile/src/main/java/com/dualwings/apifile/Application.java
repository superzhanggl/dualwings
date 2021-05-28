package com.dualwings.apifile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
//@ComponentScan({"com.dualwings.sales.*"})
//@MapperScan({"com.dualwings.sales.dao","com.dualwings.sales.dto","com.dualwings.sales.utils","com.dualwings.sales.config"})
//@EnableFeignClients
//@NacosPropertySource(dataId = "boot-user-service", autoRefreshed = true)
@EnableDiscoveryClient
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}
