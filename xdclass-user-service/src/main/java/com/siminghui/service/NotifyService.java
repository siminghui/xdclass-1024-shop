package com.siminghui.service;

import com.siminghui.enums.SendCodeEnum;
import com.siminghui.util.JsonData;

/**
 * @Author: 十七
 * @Date: 2022/6/26 4:51 下午
 * @Description:
 */
public interface NotifyService {

    /**
     * 发送验证码
     * @param sendCodeEnum
     * @param to
     * @return
     */
    JsonData sendCode(SendCodeEnum sendCodeEnum, String to);

    Boolean checkCode(SendCodeEnum sendCodeEnum, String to, String code);

}
