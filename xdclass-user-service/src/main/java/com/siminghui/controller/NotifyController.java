package com.siminghui.controller;

import com.google.code.kaptcha.Producer;
import com.siminghui.util.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 十七
 * @Date: 2022/6/26 11:01 上午
 * @Description:
 */
@Api(tags = "通知模块")
@RestController
@RequestMapping("/api/user/v1")
@Slf4j
public class NotifyController {

    @Autowired
    private Producer captchaProducer;

    // redis key规范： 业务划分，冒号隔离  1. eg: user-service:captcha:xxxx  2. key不能过长
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 图形验证码有效期10分钟(ms)
     */
    private static final long CAPTCHA_CODE_EXPIRED = 60 * 1000 * 10;

    /**
     * 获取图形验证码
     * @param request
     * @param response
     */
    @ApiOperation("获取图形验证码")
    @GetMapping("captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {

        String text = captchaProducer.createText();
        log.info("图形验证码:{}", text);

        // 存储
        redisTemplate.opsForValue().set(getCaptChakey(request),text,CAPTCHA_CODE_EXPIRED, TimeUnit.MILLISECONDS);

        BufferedImage image = captchaProducer.createImage(text);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            outputStream.flush();;
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("获取图形验证码异常:{}", e);
        }

    }

    /**
     * 获取缓存key
     * @param request
     * @return
     */
    private String getCaptChakey(HttpServletRequest request) {

        String ip = CommonUtil.getIpAddr(request);
        String userAgent = request.getHeader("User-Agent");

        String key = "user-service:captCha:" + CommonUtil.MD5(ip+userAgent);

        log.info("ip={}", ip);
        log.info("userAgent={}", userAgent);
        log.info("key={}", key);

        return key;

    }

}
