package com.siminghui;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: 十七
 * @Date: 2022/4/22 6:44 下午
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.siminghui.mapper")
@Slf4j
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
        log.info("user服务启动成功");
    }

}
