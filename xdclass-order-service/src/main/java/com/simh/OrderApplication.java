package com.simh;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: 十七
 * @Date: 2022/9/5 9:50 AM
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.simh.mapper")
@EnableTransactionManagement
@EnableFeignClients
@EnableDiscoveryClient
@Slf4j
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
        log.info("order项目启动成功");
    }

}
