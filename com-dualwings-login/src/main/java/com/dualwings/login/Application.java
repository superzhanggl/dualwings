package com.dualwings.login;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({"com.dualwings.login.*"})
//@MapperScan({"com.dualwings.basic.mapper","com.dualwings.basic.dto","com.dualwings.basic.utils","com.dualwings.basic.config"})
//@EnableFeignClients
//@NacosPropertySource(dataId = "boot-user-service", autoRefreshed = true)
//@EnableDiscoveryClient
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}
