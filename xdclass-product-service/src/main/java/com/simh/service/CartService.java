package com.simh.service;

import com.simh.request.CartItemRequest;
import com.simh.vo.CartVO;

/**
 * @Author: 十七
 * @Date: 2022/9/2 11:18 AM
 * @Description: 购物车服务
 */
public interface CartService {
    /**
     * 添加商品到购物车
     * @param cartItemRequest
     */
    void addToCart(CartItemRequest cartItemRequest);

    /**
     * 清空购物车
     */
    void clear();

    /**
     * 查看我的购物车
     * @return
     */
    CartVO getMyCart();
}
