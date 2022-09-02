package com.simh.vo;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 十七
 * @Date: 2022/9/2 10:46 AM
 * @Description: 购物车VO
 */
public class CartVO {

    @ApiModelProperty(value = "购物项")
    private List<CartItemVO> cartItemVOList;

    @ApiModelProperty(value = "购买总件数")
    private Integer totalNum;

    @ApiModelProperty(value = "购物车总价格")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "实际支付价格")
    private BigDecimal realPayPrice;

    public List<CartItemVO> getCartItemVOList() {
        return cartItemVOList;
    }

    public void setCartItemVOList(List<CartItemVO> cartItemVOList) {
        this.cartItemVOList = cartItemVOList;
    }

    /**
     * 总件数
     * @return
     */
    public Integer getTotalNum() {
        if (this.cartItemVOList != null){
            return cartItemVOList.stream().mapToInt(CartItemVO::getBuyNum).sum();
        }
        return 0;
    }

    /**
     * 总价格
     * @return
     */
    public BigDecimal getTotalAmount() {
        BigDecimal amount = new BigDecimal(0);
        if (this.cartItemVOList != null) {
            for (CartItemVO cartItemVO : cartItemVOList) {
                BigDecimal itemTotalAmount = cartItemVO.getTotalAmount();
                amount = amount.add(itemTotalAmount);
            }
            return amount;
        }
        return amount;
    }

    /**
     * 实际支付价格
     * @return
     */
    public BigDecimal getRealPayAmount() {
        BigDecimal amount = new BigDecimal(0);
        if (this.cartItemVOList != null) {
            for (CartItemVO cartItemVO : cartItemVOList) {
                BigDecimal itemTotalAmount = cartItemVO.getTotalAmount();
                amount = amount.add(itemTotalAmount);
            }
            return amount;
        }
        return amount;
    }
}
