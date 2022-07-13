package com.simh.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: 十七
 * @Date: 2022/7/2 4:25 下午
 * @Description:
 */
@ApiModel(value = "用户注册对象", description = "用户注册请求对象")
@Data
public class UserRegisterRequest {

    @ApiModelProperty(value = "昵称", example = "Anna")
    private String name;

    @ApiModelProperty(value = "密码", example = "123456")
    private String pwd;

    @ApiModelProperty(value = "头像", example = "https://simh-xdclass-1024shop-img.oss-cn-hangzhou.aliyuncs.com/user/2022/07/02/ac7d2965890f45408b984bc2823fbfd7.png")
    @JsonProperty("head_img")
    private String headImg;

    @ApiModelProperty(value = "用户个性签名", example = "做人要潇洒一点")
    private String slogan;

    @ApiModelProperty(value = "性别 0-女 1-男", example = "1")
    private Integer sex;

    @ApiModelProperty(value = "邮箱", example = "158782869@qq.com")
    private String mail;

    @ApiModelProperty(value = "验证码", example = "872345")
    private String code;

}
