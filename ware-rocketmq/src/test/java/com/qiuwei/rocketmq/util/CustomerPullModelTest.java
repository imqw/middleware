package com.qiuwei.rocketmq.util;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.impl.consumer.PullResultExt;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * 消息队列拉取模式pull 消费单元测试
 *
 * @Author: qiuwei@19pay.com.cn
 * @Date: 2020-04-10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerPullModelTest {

    private static final Map<MessageQueue, Long> offsetTable = new HashMap<MessageQueue, Long>();

    private static final String ADDR = "172.30.66.86:9876";


    @Autowired
    private ProducerMsgUtils utils;


    /**
     * 发送消息
     */
    @Test
    public void sendMsg() {

        for (int i = 0; i < 10; i++) {
            Boolean sendMsg = utils.sendMsg("TopicTest", "tagA", "Hello RocketMQ " + i);
            System.out.println("消息发送结果 : " + sendMsg);
        }

    }


    /**
     * 消费消息
     */

    @Test
    public void pullMsg() throws MQClientException {

        offsetTable.clear();
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("pullConsumer");
        consumer.setNamesrvAddr(ADDR);
        consumer.start();

        try {
            Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("TopicTest");
            for (MessageQueue mq : mqs) {
                System.out.println("当前获取的消息的归属队列是: " + mq.getQueueId());


                PullResultExt pullResult = (PullResultExt) consumer.pullBlockIfNotFound(mq, null,
                        getMessageQueueOffset(mq), 32);

                switch (pullResult.getPullStatus()) {

                    case FOUND:

                        List<MessageExt> messageExtList = pullResult.getMsgFoundList();
                        for (MessageExt m : messageExtList) {
                            System.out.println("收到了消息:" + new String(m.getBody()));
                        }
                        break;

                    case NO_MATCHED_MSG:
                        break;

                    case NO_NEW_MSG:
                        break;

                    case OFFSET_ILLEGAL:
                        break;

                    default:
                        break;
                }
            }

        } catch (Exception e) {

        }
    }


    /**
     * 生产者2 指定队列发送
     */
    @Test
    public void sendMsg2() {


        for (int i = 0; i < 10; i++) {
            SendResult sendResult = utils.sendOneQue("TopicTest", "tagA", "你好 RocketMQ " + i);
            System.out.println("当前消息投递到的队列是 : " + sendResult.getMessageQueue().getQueueId());
        }

    }

    /**
     * 消费者2  指定队列消费
     */
    @Test
    public void pullCustomer2() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

        offsetTable.clear();

        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("pullConsumer");
        consumer.setNamesrvAddr(ADDR);
        consumer.start();

        Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("TopicTest");
        for (MessageQueue mq : mqs) {

            if (mq.getQueueId() == 0) {

                PullResultExt pullResult = (PullResultExt) consumer.pullBlockIfNotFound(mq, null,
                        getMessageQueueOffset(mq), 32);
                putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
                switch (pullResult.getPullStatus()) {

                    case FOUND:

                        List<MessageExt> messageExtList = pullResult.getMsgFoundList();
                        for (MessageExt m : messageExtList) {
                            System.out.println("我是从第1个队列获取消息的");
                            System.out.println("收到了消息:" + new String(m.getBody()));
                        }
                        break;

                    case NO_MATCHED_MSG:
                        break;

                    case NO_NEW_MSG:
                        break;

                    case OFFSET_ILLEGAL:
                        break;

                    default:
                        break;
                }
            }
        }


    }


    private static void putMessageQueueOffset(MessageQueue mq, long offset) {
        offsetTable.put(mq, offset);
    }

    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = offsetTable.get(mq);
        if (offset != null)
            return offset;
        return 0;
    }
}
