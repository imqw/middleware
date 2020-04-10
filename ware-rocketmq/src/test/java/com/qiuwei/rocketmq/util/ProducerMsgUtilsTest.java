package com.qiuwei.rocketmq.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author: qiuwei@19pay.com.cn
 * @Date: 2019-12-16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerMsgUtilsTest {

    @Autowired
    private ProducerMsgUtils utils;


    /**
     * 测试同步消息
     */
    @Test
    public void send() throws InterruptedException {

        Boolean f = utils.sendMsg("test-mq", "mq-sync", "发送同步消息...");

        System.out.println(f);

        Thread.sleep(1000);

    }


    /**
     * 测试异步消息
     */
    @Test
    public void sendAsyn() throws InterruptedException {
        utils.sendAsynMsg("ware-mq","mq-asyn","发送异步消息...");
        Thread.sleep(1000);
    }


    /**
     * 测试单向消息
     */


    @Test
    public void sendOneway() throws InterruptedException {

        utils.sendOneway("ware-mq","mq-one-way","发送单向消息...");
        Thread.sleep(1000);


    }




}