package com.qiuwei.memecachedstarter;

import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;


/**
 * @Author: qiuwei@19pay.com.cn
 * @Date: 2019-12-19.
 */
@Slf4j
public class MemecachedFactory implements CommandLineRunner {


    private MemecacheProperties memcacheSource;

    private MemcachedClient client = null;

    public MemecachedFactory(MemecacheProperties memcacheSource) {
        this.memcacheSource = memcacheSource;
    }

    @Override
    public void run(String... args) throws Exception {

        try {
            client = new MemcachedClient(new InetSocketAddress(memcacheSource.getIp(), memcacheSource.getPort()));
        } catch (IOException i) {
            log.error("连接memecache异常...:{}", i);
            i.printStackTrace();
        }

    }

    public MemcachedClient getClient() {
        return client;
    }
}
