package com.simh.service;

import com.simh.request.AddressAddRequest;
import com.simh.vo.AddressVO;

import java.util.List;

/**
 * @Author: 十七
 * @Date: 2022/4/23 3:20 下午
 * @Description:
 */
public interface AddressService {

    /**
     * 查找指定地址详情
     * @param id
     * @return
     */
    AddressVO detail(Long id);

    /**
     * 新增收货地址
     * @param addressAddReqeust
     */
    void add(AddressAddRequest addressAddReqeust);

    /**
     * 根据id删除地址
     * @param addressId
     * @return
     */
    int del(int addressId);

    /**
     * 查找用户全部收货地址
     * @return
     */
    List<AddressVO> listUserAllAddress();

}
