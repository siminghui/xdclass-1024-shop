package com.simh.controller;


import com.simh.enums.BizCodeEnum;
import com.simh.service.CouponRecordService;
import com.simh.util.JsonData;
import com.simh.vo.CouponRecordVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 优惠券领劵记录 前端控制器
 * </p>
 *
 * @author 司明辉
 * @since 2022-07-13
 */
@RestController
@RequestMapping("/api/coupon_record/v1")
public class CouponRecordController {


    @Autowired
    private CouponRecordService couponRecordService;


    @ApiOperation("分页查询个人优惠券")
    @GetMapping("page")
    public JsonData page(@ApiParam(value = "当前页") @RequestParam(value = "page", defaultValue = "1") int page,
                         @ApiParam(value = "每页显示多少条") @RequestParam(value = "size", defaultValue = "10") int size) {


        Map<String, Object> pageResult = couponRecordService.page(page, size);

        return JsonData.buildSuccess(pageResult);
    }


    @ApiOperation("查询优惠券记录详情")
    @GetMapping("detail/{record_id}")
    public JsonData getCouponRecordDetail(@ApiParam(value = "记录id") @PathVariable("record_id") long recordId) {


        CouponRecordVO couponRecordVO = couponRecordService.findById(recordId);

        return couponRecordVO == null ? JsonData.buildResult(BizCodeEnum.COUPON_NO_EXITS) : JsonData.buildSuccess(couponRecordVO);

    }
}

