package com.simh.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.simh.config.OssConfig;
import com.simh.service.FileService;
import com.simh.util.CommonUtil;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @Author: 十七
 * @Date: 2022/7/2 12:37 下午
 * @Description:
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private OssConfig ossConfig;

    @Override
    public String uploadUserImg(MultipartFile file) {

        // 获取相关配置
        String bucketname = ossConfig.getBucketname();
        String endpoint = ossConfig.getEndpoint();
        String accessKeyId = ossConfig.getAccessKeyId();
        String accessKeySecret = ossConfig.getAccessKeySecret();

        // 创建oss对象
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 获取原始文件名  xxx.jpg
        String originalFilename = file.getOriginalFilename();

        // JDK8 的 日期格式化
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        // 拼装路径,oss上存储的路径    user/2022/7/2/secret.jpg
        String folder = dtf.format(ldt);
        String fileName = CommonUtil.generateUUID();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        String newFileName = "user/" + folder + "/" + fileName + extension;

        try {
            // 上传
            PutObjectResult putObjectResult = ossClient.putObject(bucketname, newFileName, file.getInputStream());

            // 拼装返回路径
            if (Objects.nonNull(putObjectResult)) {
                String imgurl = "https://" + bucketname + "." + endpoint + "/" + newFileName;
                return imgurl;
            }
        } catch (IOException e) {
            log.error("文件上传失败:{}", e);
        } finally {
            // oss关闭服务，不然会造成oom 内存泄漏
            ossClient.shutdown();
        }
        return null;

    }

    public static void main(String[] args) {
        MinioClient minioClient = null;
        try {
            // http://minio.hbos-dev.svc.cluster.local:9000/
            minioClient = new MinioClient("minio-hbos-dev.cfuture.shop",  "ikvVLFicIG", "ikvVLFicIG", Boolean.FALSE);
            InputStream inputStream = minioClient.getObject("hbos-test", "/hbos-business-inpatient-laonian/laonian-test.json");

            StringWriter writer = new StringWriter();
            IOUtils.copy(inputStream, writer, StandardCharsets.UTF_8.name());
            String str = writer.toString();

            System.out.println(str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void testMinio() {

    }


}
