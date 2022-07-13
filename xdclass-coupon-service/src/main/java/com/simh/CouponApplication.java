package com.simh;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: 十七
 * @Date: 2022/7/13 8:44 下午
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.simh.mapper")
@Slf4j
public class CouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponApplication.class, args);
        log.info("coupon项目启动成功");
    }

}
