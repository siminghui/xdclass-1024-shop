package com.simh.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: 十七
 * @Date: 2022/7/2 6:03 下午
 * @Description: 用户登录请求
 */

@ApiModel(value = "登录对象")
@Data
public class UserLoginRequest {


    @ApiModelProperty(value = "邮箱", example = "158782869@qq.com")
    private String mail;

    @ApiModelProperty(value = "密码", example = "123456")
    private String pwd;

}
