package com.siminghui.service.impl;

import com.siminghui.component.MailService;
import com.siminghui.enums.BizCodeEnum;
import com.siminghui.enums.SendCodeEnum;
import com.siminghui.service.NotifyService;
import com.siminghui.util.CheckUtil;
import com.siminghui.util.CommonUtil;
import com.siminghui.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 十七
 * @Date: 2022/6/26 4:54 下午
 * @Description:
 */
@Service
@Slf4j
public class NotifyServiceImpl implements NotifyService {

    @Autowired
    private MailService mailService;
    /**
     * 验证码的标题
     */
    private static final String SUBJECT = "小滴课堂验证码";
    /**
     * 验证码的内容
     */
    private static final String CONTENT = "您的验证码是%s,有效时间是60s";

    @Override
    public JsonData sendCode(SendCodeEnum sendCodeEnum, String to) {

        if (CheckUtil.isEmail(to)) {
            // 邮箱验证码
            String code = CommonUtil.getRandomCode(6);
            mailService.sendMail(to,SUBJECT, String.format(CONTENT, code));
            return JsonData.buildSuccess();
        }else if (CheckUtil.isPhone(to)){
            // 手机验证码

        }

        return JsonData.buildResult(BizCodeEnum.CODE_TO_ERROR);

    }
}
