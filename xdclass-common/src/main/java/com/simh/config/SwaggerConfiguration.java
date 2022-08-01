package com.simh.config;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 十七
 * @Date: 2022/4/23 3:53 下午
 * @Description:
 */
@Component
@Data
@EnableOpenApi
public class SwaggerConfiguration {

    /**
     * 对C端用户的接口文档
     * @return
     */
    //@Bean
    public Docket webApiDoc() {

        return new Docket((DocumentationType.OAS_30))
                .groupName("用户端接口文档")
                .pathMapping("/")
                // 定义是否开启swagger,false是关闭，可以通过变量取控制，线上关闭
                .enable(true)
                // 配置文档的元信息
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.simh"))
                // 正则匹配请求路径，并分配到当前项目组
                .paths(PathSelectors.ant("/api/**"))
                .build()
                // 新版swagger-ui3.0
                .globalRequestParameters(globalRequestParameters())
                .globalResponses(HttpMethod.GET,getGlobalResponseMessage())
                .globalResponses(HttpMethod.POST,getGlobalResponseMessage());

    }

    /**
     * 对管理端用户的接口文档
     * @return
     */
    //@Bean
    public Docket AdminApiDoc() {

        return new Docket((DocumentationType.OAS_30))
                .groupName("管理端接口文档")
                .pathMapping("/")
                // 定义是否开启swagger,false是关闭，可以通过变量取控制，线上关闭
                .enable(true)
                // 配置文档的元信息
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.simh"))
                // 正则匹配请求路径，并分配到当前项目组
                .paths(PathSelectors.ant("/api/**"))
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("1024电商平台").description("微服务接口文档").contact(new Contact("十七","https://siminghui.com","微信")).version("v1.0").build();
    }

    /**
     * 配置全局通用参数
     */
    private List<RequestParameter> globalRequestParameters() {

        List<RequestParameter> parameters = new ArrayList<>();
        parameters.add(new RequestParameterBuilder()
                .name("token")
                .description("登录令牌")
                .in(ParameterType.HEADER)
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .required(false)
                .build());

        // 可添加多个，看具体需求
        // parameters.add(new RequestParameterBuilder()
        //        .name("token2")
        //        .description("登录令牌")
        //        .in(ParameterType.HEADER)
        //        .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
        //        .required(false)
        //        .build());

        return parameters;
    }

    /**
     * 生成通用响应信息
     * @return
     */
    private List<Response> getGlobalResponseMessage() {
        List<Response> responseList = new ArrayList<>();
        responseList.add(new ResponseBuilder().code("4xx").description("请求错误，根据code和msg检查").build());
        return responseList;
    }

}
