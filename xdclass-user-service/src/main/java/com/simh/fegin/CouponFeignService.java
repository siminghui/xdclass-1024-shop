package com.simh.fegin;

import com.simh.request.NewUserCouponRequest;
import com.simh.util.JsonData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: 十七
 * @Date: 2022/9/6 4:37 PM
 * @Description:
 */
@FeignClient(name = "xdclass-coupon-service")
public interface CouponFeignService {

    /**
     * 新用户注册发放优惠券
     * @param newUserCouponRequest
     * @return
     */
    @PostMapping("/api/coupon/v1/new_user_coupon")
    JsonData addNewUserCoupon(@RequestBody NewUserCouponRequest newUserCouponRequest);

}
