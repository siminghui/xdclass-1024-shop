package com.siminghui.service;

import com.siminghui.request.UserRegisterRequest;
import com.siminghui.util.JsonData;

/**
 * @Author: 十七
 * @Date: 2022/7/2 4:37 下午
 * @Description:
 */
public interface UserService {

    /**
     * 用户注册
     * @param request
     * @return
     */
    JsonData register(UserRegisterRequest request);

}
