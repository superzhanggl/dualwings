package user;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@EnableFeignClients
@ComponentScan({"com.dualwings.user"})
@MapperScan({"com.dualwings.user.dao"})
public class ApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class,args);
    }

}
