package com.qiuwei.rocketmq.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 发送消息工具类
 *
 * @Author: qiuwei@19pay.com.cn
 * @Date: 2019-12-16.
 */
@Component
@Slf4j
public class ProducerMsgUtils {


    @Autowired
    private DefaultMQProducer defaultMQProducer;


    /**
     * 同步发送消息
     *
     * @param topic
     * @param tag
     * @param msg
     * @return
     */
    public Boolean sendMsg(String topic, String tag, String msg) {

        log.info("开始发送消息...topic:{},tag:{},msg:{}", topic, tag, msg);
        Message sendMsg = new Message(topic, tag, msg.getBytes());

        try {
            SendResult sendResult = defaultMQProducer.send(sendMsg);

            if (sendResult == null || SendStatus.SEND_OK != sendResult.getSendStatus()) {
                log.error("消息发送失败... : {}", sendResult.toString());
                return false;
            }
            log.info("消息发送成功 结果: {}", sendResult.toString());
        } catch (Exception e) {
            log.error("消息发送失败...topic:{},tag:{},msg:{}", topic, tag, msg);
            return false;

        }
        return true;
    }


    /**
     * 异步发送消息
     */

    public void sendAsynMsg(String topic, String tag, String msg) {
        log.info("开始发送异步消息...topic:{},tag:{},msg:{}", topic, tag, msg);
        Message message = new Message(topic, tag, msg.getBytes());

        try {
            defaultMQProducer.send(message, new SendCallback() {

                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("异步消息发送成功:{}", sendResult.toString());
                }

                @Override
                public void onException(Throwable throwable) {
                    log.error("消息发送失败...topic:{},tag:{},msg:{}", topic, tag, msg);
                    log.error("异步消息发送失败失败信息...:{}", throwable.toString());
                    throwable.printStackTrace();
                }
            });

        } catch (Exception e) {
            log.error("异步消息发送异常...topic:{},tag:{},msg:{}", topic, tag, msg);

        }

    }


    /**
     * 单向发送
     */
    public void sendOneway(String topic, String tag, String msg) {
        log.info("开始发送单向消息...topic:{},tag:{},msg:{}", topic, tag, msg);

        Message message = new Message(topic, tag, msg.getBytes());

        try {
            defaultMQProducer.sendOneway(message);

        } catch (Exception e) {
            log.info("发送单向消息失败...topic:{},tag:{},msg:{}", topic, tag, msg);
        }
    }


    /**
     * 发送到同一个消息队列
     */

    public SendResult sendOneQue(String topic, String tag, String msg) {

        log.info("发送定向队列消息...topic:{},tag:{},msg:{}",topic, tag, msg);

        Message message = new Message(topic, tag, msg.getBytes());
        SendResult sendResult = null;
        try {
            sendResult = defaultMQProducer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> queues, Message msg, Object queNum) {
                    int queueNum = Integer.parseInt(queNum.toString());
                    return queues.get(queueNum);
                }
            }, 0);
        } catch (Exception e) {
            log.error("发送定向队列消息失败...topic:{},tag:{},msg:{}",topic, tag, msg);
        }
        log.info("发送定向队列消息成功...结果码:{}",sendResult.getSendStatus());
        return sendResult;


    }


}
