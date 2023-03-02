package com.simh.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: 十七
 * @Date: 2022/7/2 12:35 下午
 * @Description:
 */
public interface FileService {

    String uploadUserImg(MultipartFile file);

    void testMinio();

}
