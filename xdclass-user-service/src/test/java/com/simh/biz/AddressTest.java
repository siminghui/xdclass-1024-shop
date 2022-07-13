package com.simh.biz;

import com.simh.UserApplication;
import com.simh.service.AddressService;
import com.simh.vo.AddressVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: 十七
 * @Date: 2022/4/23 5:22 下午
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class AddressTest {

    @Autowired
    private AddressService addressService;

    @Test
    public void testAddressDetail() {

        AddressVO detail = addressService.detail(1L);

        log.info(detail.toString());
        Assert.assertNotNull(detail);

    }

}
