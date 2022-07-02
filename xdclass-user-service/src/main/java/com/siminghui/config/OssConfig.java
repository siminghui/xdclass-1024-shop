package com.siminghui.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 十七
 * @Date: 2022/7/2 12:21 下午
 * @Description: oss配置类
 */
@ConfigurationProperties(prefix = "aliyun.oss")
@Configuration
@Data
public class OssConfig {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketname;

}
