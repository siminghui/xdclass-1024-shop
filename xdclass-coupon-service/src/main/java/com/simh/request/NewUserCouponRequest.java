package com.simh.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * @Author: 十七
 * @Date: 2022/8/19 4:22 PM
 * @Description:
 */
@Data
@ApiModel
public class NewUserCouponRequest {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "名称")
    private String name;

}
