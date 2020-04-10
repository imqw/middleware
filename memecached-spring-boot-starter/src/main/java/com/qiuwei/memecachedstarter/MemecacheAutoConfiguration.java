package com.qiuwei.memecachedstarter;

import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * memecache缓存自动注入类
 *
 * @Author: qiuwei@19pay.com.cn
 * @Date: 2019-12-20.
 */
@Configuration
@EnableConfigurationProperties(MemecacheProperties.class)
@ConditionalOnClass(MemecachedFactory.class)
@ConditionalOnProperty(prefix = "memecache", value = "enabled", matchIfMissing = true)
public class MemecacheAutoConfiguration {


    private MemecachedFactory memecachedFactory = null;

    @Autowired
    private MemecacheProperties memecacheProperties;

    @Bean
    @ConditionalOnMissingBean(MemecachedFactory.class)
    public MemecachedFactory getClient() {

        if (memecachedFactory == null) {
            memecachedFactory = new MemecachedFactory(memecacheProperties);
        }
        return memecachedFactory;
    }

}
