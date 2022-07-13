package com.simh.service;

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

}
