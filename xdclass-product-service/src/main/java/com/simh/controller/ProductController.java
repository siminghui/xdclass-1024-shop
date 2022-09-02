package com.simh.controller;


import com.simh.service.ProductService;
import com.simh.util.JsonData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author 司明辉
 * @since 2022-08-22
 */
@Api("商品模块")
@RestController
@RequestMapping("/api/product/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation("分页查询商品列表")
    @GetMapping("page")
    public JsonData pageProductList(
            @ApiParam(value = "当前页")  @RequestParam(value = "page", defaultValue = "1") int page,
            @ApiParam(value = "每页显示多少条")  @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Map<String, Object> pageResult = productService.page(page, size);

        return JsonData.buildSuccess(pageResult);
    }

}

