package com.simh.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: 十七
 * @Date: 2022/9/2 10:47 AM
 * @Description: 购物车明细项
 */
@Data
public class CartItemVO {

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @ApiModelProperty(value = "购买数量")
    private Integer buyNum;

    @ApiModelProperty(value = "商品标题")
    private String productTitle;

    @ApiModelProperty(value = "图片")
    private String productImg;

    @ApiModelProperty(value = "单价")
    private BigDecimal amount;

    @ApiModelProperty(value = "总价格 单价*数量")
    private BigDecimal totalAmount;

    public BigDecimal getTotalAmount() {
        return this.amount.multiply(new BigDecimal(this.buyNum));
    }
}
