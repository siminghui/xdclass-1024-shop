package com.simh.service;

import com.simh.enums.CouponCategoryEnum;
import com.simh.request.NewUserCouponRequest;
import com.simh.util.JsonData;

import java.util.Map;

/**
 * @Author: 十七
 * @Date: 2022/7/13 10:26 下午
 * @Description:
 */
public interface CouponService {

    /**
     * 分页查询优惠券
     * @param page
     * @param size
     * @return
     */
    Map<String, Object> pageCouponActivity(int page, int size);

    /**
     * 领取优惠券
     * @param couponId
     * @param promotion
     */
    JsonData addCoupon(long couponId, CouponCategoryEnum promotion);

    /**
     * 新用户注册发放优惠券
     * @param newUserCouponRequest
     * @return
     */
    JsonData initNewUserCoupon(NewUserCouponRequest newUserCouponRequest);
}
