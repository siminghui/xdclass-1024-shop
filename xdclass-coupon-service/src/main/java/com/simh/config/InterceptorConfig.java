package com.simh.config;

import com.simh.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: 十七
 * @Date: 2022/7/11 10:15 下午
 * @Description:
 */
@Configuration
@Slf4j
public class InterceptorConfig implements WebMvcConfigurer   {

    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                // 拦截的路径
                .addPathPatterns("/api/coupon/*/**","/api/coupon_record/*/**")
                // 排除不拦截的路径
                .excludePathPatterns("/api/coupon/*/page_record", "/api/coupon/*/new_user_coupon");

    }
}
