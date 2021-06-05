package com.dualwings.sales;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@MapperScan({"com.dualwings.sales.mapper"})
@EnableFeignClients
//@NacosPropertySource(dataId = "boot-user-service", autoRefreshed = true)
@EnableDiscoveryClient
public class SalesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalesApplication.class,args);
        System.out.println("(♥◠‿◠)ﾉﾞ  sales模块启动成功   ლ(´ڡ`ლ)ﾞ " +
                "\n" +
                " SSS  U   U  CCC  CCC EEEE  SSS   SSS  \n" +
                "S     U   U C    C    E    S     S     \n" +
                " SSS  U   U C    C    EEE   SSS   SSS  \n" +
                "    S U   U C    C    E        S     S \n" +
                "SSSS   UUU   CCC  CCC EEEE SSSS  SSSS ");
    }

}
