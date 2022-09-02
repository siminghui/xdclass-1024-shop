package com.simh.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: 十七
 * @Date: 2022/9/2 11:21 AM
 * @Description:
 */
@ApiModel
@Data
public class CartItemRequest {

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @ApiModelProperty(value = "")
    private Integer buyNum;

}
