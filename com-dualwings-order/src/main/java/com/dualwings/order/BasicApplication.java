package com.dualwings.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({ "com.dualwings.order.*" })
@MapperScan({ "com.dualwings.order.mapper", "com.dualwings.order.domain.entity",
		"com.dualwings.order.config" })
//@EnableFeignClients
//@NacosPropertySource(dataId = "boot-user-service", autoRefreshed = true)
//@EnableDiscoveryClient
public class BasicApplication {

    public static void main(String[] args) {
		SpringApplication.run(BasicApplication.class, args);
    }

}
