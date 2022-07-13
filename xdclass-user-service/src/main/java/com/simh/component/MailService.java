package com.simh.component;

/**
 * @Author: 十七
 * @Date: 2022/6/26 1:19 下午
 * @Description: 邮件服务
 */
public interface MailService {

    /**
     * 发送邮件
     * @param to
     * @param subject
     * @param content
     */
    void sendMail(String to, String subject, String content);

}
