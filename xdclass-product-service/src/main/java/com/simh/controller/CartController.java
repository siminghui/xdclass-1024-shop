package com.simh.controller;

import com.simh.request.CartItemRequest;
import com.simh.service.CartService;
import com.simh.util.JsonData;
import com.simh.vo.CartVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

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

    @ApiOperation("查看我的购物车")
    @GetMapping("/myCart")
    public JsonData findMyCart() {
        CartVO cartVO = cartService.getMyCart();
        return JsonData.buildSuccess(cartVO);

    }

    @ApiOperation("删除购物项")
    @DeleteMapping("/delete/{product_id}")
    public JsonData deleteItem(@ApiParam(value = "商品id", required = true) @PathVariable("product_id") Long productId) {
        cartService.deleteItem(productId);
        return JsonData.buildSuccess();
    }

    @ApiOperation("修改购物车数量")
    @PostMapping("/change")
    public JsonData changeItem(@ApiParam(value = "商品id", required = true) @RequestBody CartItemRequest cartItemRequest) {
        cartService.changeItemNum(cartItemRequest);
        return JsonData.buildSuccess();
    }

}
