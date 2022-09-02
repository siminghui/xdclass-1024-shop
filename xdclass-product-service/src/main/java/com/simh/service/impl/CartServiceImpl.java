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
import com.simh.vo.CartVO;
import com.simh.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


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
     * 查看我的购物车
     * @return
     */
    @Override
    public CartVO getMyCart() {
        // 获取全部购物项
        List<CartItemVO> cartItemVOList = buildCartItem(false);

        // 封装成CartVO
        CartVO cartVO = new CartVO();
        cartVO.setCartItemVOList(cartItemVOList);

        return cartVO;
    }

    /**
     * 获取最新的购物项  是否获取最新价格
     * @param latestPrice
     * @return
     */
    private List<CartItemVO> buildCartItem(boolean latestPrice) {
        BoundHashOperations<String, Object, Object> myCart = getMyCartOps();

        List<Object> itemList = myCart.values();

        List<CartItemVO> cartItemVOList = new ArrayList<>();
        // 拼接id列表用于查询最新价格
        List<Long> productIdList = new ArrayList<>();

        for (Object item : itemList) {
            CartItemVO cartItemVO = JSON.parseObject((String) item, CartItemVO.class);
            cartItemVOList.add(cartItemVO);
            productIdList.add(cartItemVO.getProductId());
        }

        // 查询最新的商品价格
        if (latestPrice) {
            setProductLatestPrice(cartItemVOList, productIdList);
        }

        return cartItemVOList;
    }

    /**
     * 设置商品最新价格
     * @param productIdList
     */
    private void setProductLatestPrice(List<CartItemVO> cartItemVOList, List<Long> productIdList) {
        // 批量查询
        List<ProductVO> productVOList = productService.findProductByIdBatch(productIdList);
        // 根据id分组
        Map<Long, ProductVO> maps = productVOList.stream().collect(Collectors.toMap(ProductVO::getId, Function.identity()));

        cartItemVOList.stream().forEach(item -> {
            ProductVO productVO = maps.get(item.getProductId());
            item.setProductTitle(productVO.getTitle());
            item.setProductImg(productVO.getCoverImg());
            item.setAmount(productVO.getPrice());
        });

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
