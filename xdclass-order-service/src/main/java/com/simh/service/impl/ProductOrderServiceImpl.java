package com.simh.service.impl;

import com.simh.request.ConfirmOrderRequest;
import com.simh.service.ProductOrderService;
import com.simh.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: 十七
 * @Date: 2022/9/6 2:17 PM
 * @Description:
 */
@Service
@Slf4j
public class ProductOrderServiceImpl implements ProductOrderService {
    @Override
    public JsonData confirmOrder(ConfirmOrderRequest orderRequest) {
        return null;
    }
}
