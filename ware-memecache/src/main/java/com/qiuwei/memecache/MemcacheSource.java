package com.qiuwei.memecache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: qiuwei@19pay.com.cn
 * @Date: 2019-12-19.
 */
@Component
@ConfigurationProperties(value = "memecache")
@Data
public class MemcacheSource {

    private String ip;

    private int port;

}
