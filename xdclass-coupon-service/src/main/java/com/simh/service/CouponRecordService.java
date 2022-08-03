package com.simh.service;

import com.simh.vo.CouponRecordVO;

import java.util.Map;

/**
 * @Author: 十七
 * @Date: 2022/8/3 8:01 PM
 * @Description:
 */
public interface CouponRecordService {

    /**
     * 分页查询领劵记录
     * @param page
     * @param size
     * @return
     */
    Map<String,Object> page(int page, int size);

    /**
     * 根据id查询详情
     * @param recordId
     * @return
     */
    CouponRecordVO findById(long recordId);

}
