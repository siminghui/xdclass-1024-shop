package com.simh.constant;

/**
 * @Author: 十七
 * @Date: 2022/7/2 9:28 上午
 * @Description:
 */
public class CacheKey {

    /**
     * 注册验证码，第一个是类型，第二个是接收号码
     */
    public static final String CHECK_CODE_KEY = "code:%s:%s";

    /**
     * 购物车 hash key是用户唯一标识
     */
    public static final String CART_KEY = "cart:%s";



}
