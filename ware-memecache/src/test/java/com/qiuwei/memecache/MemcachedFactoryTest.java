package com.qiuwei.memecache;

import net.spy.memcached.MemcachedClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: qiuwei@19pay.com.cn
 * @Date: 2019-12-19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemcachedFactoryTest {


    @Autowired
    private MemcachedFactory memcachedFactory;

    @Test
    public void testAdd() {
        MemcachedClient client = memcachedFactory.getClient();

        client.set("testkey",1000,"666666");

        Object testkey = client.get("testkey");

        System.out.println(testkey);


    }


}