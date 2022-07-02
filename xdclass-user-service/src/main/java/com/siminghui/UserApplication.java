package com.siminghui;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.spring.web.json.Json;

/**
 * @Author: 十七
 * @Date: 2022/4/22 6:44 下午
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.siminghui.mapper")
@Slf4j
public class UserApplication {

    @Value("${test.profile:}")
    private String testProfile;
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
        log.info("user服务启动成功");
    }

    @Bean("testProfile")
    public void testProfile() {
        log.info("环境:{}", testProfile);
    }

}
