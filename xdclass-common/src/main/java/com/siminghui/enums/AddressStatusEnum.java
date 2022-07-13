package com.siminghui.enums;

/**
 * @Author: 十七
 * @Date: 2022/7/13 7:54 下午
 * @Description:
 */
public enum AddressStatusEnum {

    /**
     * 是默认收货地址
     */
    DEFAULT_STATUS(1),

    /**
     * 非默认收货地址
     */
    COMMON_STATUS(0);

    private int status;

    private AddressStatusEnum(int status){
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}
