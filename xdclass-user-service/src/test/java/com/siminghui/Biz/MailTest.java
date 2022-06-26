package com.siminghui.Biz;

import com.siminghui.UserApplication;
import com.siminghui.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: 十七
 * @Date: 2022/6/26 1:26 下午
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class MailTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testSendMail() {

        mailService.sendMail("158782869@qq.com", "测试发邮件", "哈哈哈，洗头去吧");

    }

}
