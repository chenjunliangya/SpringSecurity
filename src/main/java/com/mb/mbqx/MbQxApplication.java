package com.mb.mbqx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@MapperScan("com.mb.mbqx.mapper")
//开启注解功能
@EnableGlobalMethodSecurity(securedEnabled=true,prePostEnabled = true)
public class MbQxApplication {

    public static void main(String[] args) {
        SpringApplication.run(MbQxApplication.class, args);
    }

}
