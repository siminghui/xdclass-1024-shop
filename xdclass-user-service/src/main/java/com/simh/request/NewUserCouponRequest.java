package com.simh.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class NewUserCouponRequest {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "名称")
    private String name;

}
