package com.qiuwei.memecachedstarter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: qiuwei@19pay.com.cn
 * @Date: 2019-12-20.
 */
@Component
@ConfigurationProperties("memecache")
@Data
public class MemecacheProperties {

    private static final int INI_PORT = 11211;

    private String ip;

    private int port = INI_PORT;

}
