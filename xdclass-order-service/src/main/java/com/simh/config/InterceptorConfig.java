package com.simh.config;

import com.simh.interceptor.LoginInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: 十七
 * @Date: 2022/9/5 9:56 AM
 * @Description: order项目拦截器
 */
public class InterceptorConfig implements WebMvcConfigurer {

    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                // 拦截的路径
                .addPathPatterns("/api/order/*/**")
                // 排除不拦截的路径
                .excludePathPatterns("/api/callback/*/**","/api/order/*/query");
    }

}
