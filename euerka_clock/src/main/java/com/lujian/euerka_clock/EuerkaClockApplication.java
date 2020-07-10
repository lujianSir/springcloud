package com.lujian.euerka_clock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients  //启用feign进行远程调用
@EnableEurekaClient
@MapperScan("com.lujian.euerka_clock.mapper") // 扫描的mapper
public class EuerkaClockApplication {

    public static void main(String[] args) {
        SpringApplication.run(EuerkaClockApplication.class, args);
    }

}
