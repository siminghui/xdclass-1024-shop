package com.simh.biz;

import com.simh.UserApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class MD5Test {



    @Test
    public void testMD5() {

        String salt = "$1$3GPbKpzu";
        String pwd = "123456";
        String s = Md5Crypt.md5Crypt(pwd.getBytes(), salt);
        log.info("密码:{}", s);
    }

}
