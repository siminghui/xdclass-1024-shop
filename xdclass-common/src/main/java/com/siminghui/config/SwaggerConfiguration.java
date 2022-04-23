package com.siminghui.config;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author: 十七
 * @Date: 2022/4/23 3:53 下午
 * @Description:
 */
@Component
@Data
@EnableOpenApi
public class SwaggerConfiguration {

    @Bean
    public Docket webApiDoc() {

        return new Docket((DocumentationType.OAS_30))
                .groupName("用户端接口文档")
                .pathMapping("/")
                // 定义是否开启swagger,false是关闭，可以通过变量取控制，线上关闭
                .enable(true)
                // 配置文档的元信息
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.siminghui"))
                // 正则匹配请求路径，并分配到当前项目组
                .paths(PathSelectors.ant("/api/**"))
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("1024电商平台").description("微服务接口文档").contact(new Contact("十七","https://siminghui.com","微信")).version("v1.0").build();
    }

}
