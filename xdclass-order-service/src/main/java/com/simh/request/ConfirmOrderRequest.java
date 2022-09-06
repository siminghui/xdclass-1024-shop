package com.simh.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 十七
 * @Date: 2022/9/6 2:30 PM
 * @Description: 确认订单请求
 */
@Data
public class ConfirmOrderRequest {

    /**
     * 购物车使用的优惠券，即满减券
     * -
     * 如果传空或小于0，则不用优惠券
     */
    private Long couponRecordId;

    /**
     * 最终购买的商品列表
     * -
     * 传递id，购买数量从购物车中读取
     */
    private List<Long> productIdList;

    /**
     * 支付方式
     */
    private String payType;

    /**
     * 端类型
     */
    private String clientType;

    /**
     * 收货地址id
     */
    private Long addressId;

    /**
     * 总价格，前段传递过来，后端需要验价
     */
    private BigDecimal totalAmount;

    /**
     * 实际支付价格
     * 如果用了优惠券则是减去优惠券后的价格
     * 如果没有，则==totalAmount
     */
    private BigDecimal realPayAmount;

    /**
     * 防重令牌
     */
    private String token;

}
