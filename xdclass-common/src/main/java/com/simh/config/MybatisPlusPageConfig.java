package com.simh.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 十七
 * @Date: 2022/7/13 10:18 下午
 * @Description:
 */
@Configuration
public class MybatisPlusPageConfig {


    /**
     * 旧版
     * @return
     */
    //@Bean
    //public PaginationInterceptor paginationInterceptor() {
    //    return new PaginationInterceptor();
    //}

    /**
     * 新版的分页插件配置
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());

        return mybatisPlusInterceptor;

    }

}
