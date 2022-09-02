package com.simh.controller;

import com.simh.request.CartItemRequest;
import com.simh.service.CartService;
import com.simh.util.JsonData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 十七
 * @Date: 2022/9/2 11:17 AM
 * @Description:
 */
@Api("购物车")
@RestController
@RequestMapping("/api/cart/v1")
public class CartController {

    @Autowired
    private CartService cartService;

    @ApiOperation("添加至购物车")
    @PostMapping("/add")
    public JsonData addToCart(@ApiParam("购物项") @RequestBody CartItemRequest cartItemRequest) {
        cartService.addToCart(cartItemRequest);
        return JsonData.buildSuccess();
    }

    @ApiOperation("清空购物车")
    @DeleteMapping("/clear")
    public JsonData clearMyCart() {
        cartService.clear();
        return JsonData.buildSuccess();
    }

}
