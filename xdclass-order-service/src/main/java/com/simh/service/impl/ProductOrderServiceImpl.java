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

    /**
     * 防重提交
     * 用户微服务-确认收货地址
     * 商品微服务-获取最新购物项和价格
     * 订单验价
     *      优惠券微服务-获取优惠券
     *      验证价格
     * 锁定优惠券
     * 锁定商品库存
     * 创建订单对象
     * 创建子订单对象
     * 发送延迟消息-用于自动关单
     * 创建支付信息-对接三方支付
     * @param orderRequest
     * @return
     */
    @Override
    public JsonData confirmOrder(ConfirmOrderRequest orderRequest) {
        return null;
    }
}
