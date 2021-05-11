package com.qiuwei.lock;

import org.junit.runner.RunWith;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author qiuweib@yonyou.com
 * @date 2021/4/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {


    @Autowired
    private RedissonClient redissonClient;
    /**
     * 测试redis客户端redisson（redis分布式锁测试）
     * @return
     * @throws InterruptedException
     */


    @org.junit.Test
    public void testRedissonClient() throws InterruptedException {
        RLock rLock = redissonClient.getLock("abc");
        boolean lock = rLock.tryLock(10, TimeUnit.SECONDS);
        System.out.println("thread1 lock."+ lock);
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread2 start.");
                RLock rLock = redissonClient.getLock("abc");
                boolean lock = false;
                try {
                    lock = rLock.tryLock(30, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread2 lock:" + lock);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (lock){
                    rLock.unlock();
                    System.out.println("thread2 unlock.");
                }

            }
        });
        thread.start();
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        rLock.unlock();
        System.out.println("thread1 unlock.");
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //设置读取值
        RAtomicLong rAtomicLong = redissonClient.getAtomicLong("a");
        RBucket<String> kv2 = redissonClient.getBucket("1");
        System.out.println(kv2.get());

    }

    /**
     * 测试redisson延迟队列
     */
    @org.junit.Test
    public void testRedissonBlocking() {
        RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque("testBlocking");
        //延迟队列take时，需要执行该方法先获取阻塞队列（不执行此步骤，重启时，如果延迟队列中不放数据，便不会消费之前的数据）
        RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread add start.");
                RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque("testBlocking");
                RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
                delayedQueue.offer("1", 5, TimeUnit.SECONDS);
                delayedQueue.offer("2", 10, TimeUnit.SECONDS);
                delayedQueue.offer("3", 15, TimeUnit.SECONDS);
                delayedQueue.offer("4", 20, TimeUnit.SECONDS);
                delayedQueue.offer("5", 25, TimeUnit.SECONDS);
                delayedQueue.destroy();
                System.out.println("thread add end.");
            }
        });
        thread.start();
        while (true){
            String redPacketMessage = null;
            try {
                redPacketMessage = blockingDeque.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("红包过期。id:" + redPacketMessage);
        }
    }
}
