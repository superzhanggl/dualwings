package com.dualwings.apisystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration
//@ComponentScan({"com.dualwings.apisystem.*"})
//@MapperScan({"com.dualwings.apisystem.mapper","com.dualwings.apisystem.dto","com.dualwings.apisystem.utils","com.dualwings.apisystem.config"})
//@EnableFeignClients
//@NacosPropertySource(dataId = "boot-user-service", autoRefreshed = true)
//@EnableDiscoveryClient
public class ApiSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiSystemApplication.class,args);
        System.out.println("(♥◠‿◠)ﾉﾞ  API模块启动成功   ლ(´ڡ`ლ)ﾞ " +
                "\n" +
                " SSS  U   U  CCC  CCC EEEE  SSS   SSS  \n" +
                "S     U   U C    C    E    S     S     \n" +
                " SSS  U   U C    C    EEE   SSS   SSS  \n" +
                "    S U   U C    C    E        S     S \n" +
                "SSSS   UUU   CCC  CCC EEEE SSSS  SSSS ");
    }

}
