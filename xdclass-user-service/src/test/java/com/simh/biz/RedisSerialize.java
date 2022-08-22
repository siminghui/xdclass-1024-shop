package com.simh.biz;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.ObjectOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @Author: 十七
 * @Date: 2022/8/17 1:58 PM
 * @Description:
 */
public class RedisSerialize {



    @Test
    public void testSerializa() {

        try {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bao);
            String one = "1";
            oos.writeObject(one);
            System.out.println(bao.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testNull() {
        Integer one = null;
        System.out.println(one == 1);
    }

    @Test
    public void addSecond() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        Date date = new Date();
        Date date1 = DateUtils.addSeconds(date, 5);
        System.out.println(sdf.format(date));
        System.out.println(sdf.format(date1));
    }

}
