package com.qiuwei.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: qiuwei@19pay.com.cn
 * @Date: 2019-12-16.
 */
@Component
@Slf4j
public class MyMessageListenerConcurrently implements MessageListenerConcurrently {


    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

        if (CollectionUtils.isEmpty(msgs)) {
            log.error("接受到的消息为空，不处理");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        /**
         * 设置的消费端一次消费一条消息
         */
        MessageExt messageExt = msgs.get(0);
        log.info("接受到的消息:{}", messageExt.toString());


        String topic = messageExt.getTopic();

        String tags = messageExt.getTags();

        log.info("接收到的 topic :{},tags:{}", topic, tags);


        String info = new String(messageExt.getBody());

        log.info("消息内容 :{}", info);

        /**
         * 判断该消息是否重复消费（RocketMQ不保证消息不重复，如果你的业务需要保证严格的不重复消息，需要你自己在业务端去重）
         * 获取该消息重试次数
         */
        int reconsume = messageExt.getReconsumeTimes();
        //消息已经重试了3次，如果不需要再次消费，则返回成功
        if (reconsume == 3) {
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
