package com.simh.controller;


import com.simh.service.BannerService;
import com.simh.util.JsonData;
import com.simh.vo.BannerVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 轮播图 前端控制器
 * </p>
 *
 * @author 司明辉
 * @since 2022-08-22
 */
@Api("轮播图模块")
@RestController
@RequestMapping("/api/banner/v1")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @ApiOperation("轮播图列表接口")
    @GetMapping("/list")
    public JsonData list(){

        return JsonData.buildSuccess(bannerService.list());
    }

}

