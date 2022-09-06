package com.simh.service;

import com.simh.request.ConfirmOrderRequest;
import com.simh.util.JsonData;

/**
 * @Author: 十七
 * @Date: 2022/9/6 2:17 PM
 * @Description:
 */
public interface ProductOrderService {

    /**
     * 创建订单
     * @param orderRequest
     * @return
     */
    JsonData confirmOrder(ConfirmOrderRequest orderRequest);
}
