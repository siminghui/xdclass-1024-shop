package com.simh.vo;

import lombok.Data;

/**
 * @Author: 十七
 * @Date: 2022/8/22 9:41 PM
 * @Description:
 */
@Data
public class BannerVO {

    private Integer id;

    /**
     * 图片
     */
    private String img;

    /**
     * 跳转地址
     */
    private String url;

    /**
     * 权重
     */
    private Integer weight;

}
