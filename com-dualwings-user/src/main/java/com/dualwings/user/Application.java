package com.dualwings.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({"com.dualwings.user"})
@MapperScan({"com.dualwings.user.mapper"})
//@EnableFeignClients
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}
