package com.simh.exception;

import com.simh.enums.BizCodeEnum;
import lombok.Data;

/**
 * @Author: 十七
 * @Date: 2022/4/23 5:00 下午
 * @Description:
 */
@Data
public class BizException extends RuntimeException{

    private int code;
    private String msg;

    public BizException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BizException(BizCodeEnum bizCodeEnum) {
        super(bizCodeEnum.getMessage());
        this.code = bizCodeEnum.getCode();
        this.msg = bizCodeEnum.getMessage();
    }

}
