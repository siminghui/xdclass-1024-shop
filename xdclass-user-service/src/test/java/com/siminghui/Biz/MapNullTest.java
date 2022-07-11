package com.siminghui.Biz;

import com.siminghui.UserApplication;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @Author: 十七
 * @Date: 2022/7/7 7:18 下午
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class MapNullTest {


    @Test
    public static void main(String[] args) {

        Long id = 3L;
        String value = "qwe";
        Long test =null;
        Map<Long, String> map = Maps.newHashMap(id, value);


        System.out.println(map.get(test));
    }

}
