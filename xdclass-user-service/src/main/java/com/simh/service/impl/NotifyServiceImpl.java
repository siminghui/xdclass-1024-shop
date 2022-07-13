package com.simh.service.impl;

import com.simh.component.MailService;
import com.simh.constant.CacheKey;
import com.simh.enums.BizCodeEnum;
import com.simh.enums.SendCodeEnum;
import com.simh.service.NotifyService;
import com.simh.util.CheckUtil;
import com.simh.util.CommonUtil;
import com.simh.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
    private static final String CONTENT = "您的验证码是%s,有效时间是10min";

    /**
     * 10分钟有效
     */
    private static final int CODE_EXPIRED = 60 * 1000 * 10;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 前置判断，判断是否重复发送
     *
     * 1存储验证码到缓存
     * 2发送邮箱验证码
     * （1，2组成原子操作）
     *
     * 后置：存储发送记录
     * @param sendCodeEnum
     * @param to
     * @return
     */
    @Override
    public JsonData sendCode(SendCodeEnum sendCodeEnum, String to) {

        String cacheKey = String.format(CacheKey.CHECK_CODE_KEY, sendCodeEnum.name(), to);

        String cacheValue = redisTemplate.opsForValue().get(cacheKey);

        // 如果不为空，则判断是否60s内重复发送
        if (StringUtils.isNotBlank(cacheValue)) {
            long ttl = Long.parseLong(cacheValue.split("_")[1]);
            // 当前时间戳 - 验证码发送时间戳， 如果小雨60秒，则不给重复发送
            if (CommonUtil.getCurrentTimestamp() - ttl < 1000 * 60) {
                log.info("重复发送验证码，时间间隔:{} s", (CommonUtil.getCurrentTimestamp() - ttl)/1000);
                return JsonData.buildResult(BizCodeEnum.CODE_LIMITED);
            }

        }

        // 拼接验证码
        String code = CommonUtil.getRandomCode(6);
        String value = code + "_" + CommonUtil.getCurrentTimestamp();

        redisTemplate.opsForValue().set(cacheKey, value, CODE_EXPIRED, TimeUnit.MILLISECONDS);

        if (CheckUtil.isEmail(to)) {
            // 邮箱验证码

            mailService.sendMail(to,SUBJECT, String.format(CONTENT, code));
            return JsonData.buildSuccess();
        }else if (CheckUtil.isPhone(to)){
            // 手机验证码

        }

        return JsonData.buildResult(BizCodeEnum.CODE_TO_ERROR);

    }

    /**
     * 验证验证码
     * @param sendCodeEnum
     * @param to
     * @param code
     * @return
     */
    @Override
    public Boolean checkCode(SendCodeEnum sendCodeEnum, String to, String code) {
        String cacheKey = String.format(CacheKey.CHECK_CODE_KEY, sendCodeEnum.name(), to);

        String cacheValue = redisTemplate.opsForValue().get(cacheKey);
        if (StringUtils.isNotBlank(cacheValue)) {

            String cacheCode = cacheValue.split("_")[0];
            if (Objects.equals(cacheCode, code)) {
                // 删除key
                redisTemplate.delete(cacheKey);
                return true;
            }

        }

        return false;
    }
}
