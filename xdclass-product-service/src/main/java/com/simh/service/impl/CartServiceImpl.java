package com.simh.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.simh.constant.CacheKey;
import com.simh.enums.BizCodeEnum;
import com.simh.exception.BizException;
import com.simh.interceptor.LoginInterceptor;
import com.simh.model.LoginUser;
import com.simh.request.CartItemRequest;
import com.simh.service.CartService;
import com.simh.service.ProductService;
import com.simh.vo.CartItemVO;
import com.simh.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


/**
 * @Author: 十七
 * @Date: 2022/9/2 11:18 AM
 * @Description: 购物车实现
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductService productService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void addToCart(CartItemRequest cartItemRequest) {
        Long productId = cartItemRequest.getProductId();
        Integer buyNum = cartItemRequest.getBuyNum();

        // 获取购物车
        BoundHashOperations<String, Object, Object> myCart = getMyCartOps();

        Object cacheObj = myCart.get(productId);
        String result = "";

        if (cacheObj != null) {
            result = (String)cacheObj;
        }

        if (StringUtils.isBlank(result)) {
            // 不存在，新建一个商品
            CartItemVO cartItemVO = new CartItemVO();

            ProductVO productVO = productService.findDetailById(productId);
            if (productVO == null) {throw new BizException(BizCodeEnum.CART_FAIL);}

            cartItemVO.setAmount(productVO.getPrice());
            cartItemVO.setBuyNum(buyNum);
            cartItemVO.setProductId(productId);
            cartItemVO.setProductTitle(productVO.getTitle());
            cartItemVO.setProductImg(productVO.getCoverImg());

            myCart.put(productId, JSON.toJSONString(cartItemVO));

        }else {
            // 存在商品，修改数量
            CartItemVO cartItemVO = JSON.parseObject(result, CartItemVO.class);
            cartItemVO.setBuyNum(cartItemRequest.getBuyNum() + buyNum);
            myCart.put(productId, JSON.toJSONString(cartItemVO));
        }
    }

    /**
     * 清空购物车
     */
    @Override
    public void clear() {
        String cartKey = getCartKey();
        redisTemplate.delete(cartKey);
    }


    /**
     * 抽取我的购物车， 通用方法
     * @return
     */
    private BoundHashOperations<String, Object, Object> getMyCartOps() {
        String cartKey = getCartKey();
        return redisTemplate.boundHashOps(cartKey);
    }

    /**
     * 购物车 key
     * @return
     */
    private String getCartKey() {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        String cartKey = String.format(CacheKey.CART_KEY, loginUser.getId());
        return cartKey;
    }
}
