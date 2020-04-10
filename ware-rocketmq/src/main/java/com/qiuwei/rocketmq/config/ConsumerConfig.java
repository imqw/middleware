package com.qiuwei.rocketmq.config;

import com.qiuwei.rocketmq.exception.RocketMQException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

/**
 * rocketmq 消费者配置中心
 *
 * @Author: qiuwei@19pay.com.cn
 * @Date: 2019-12-16.
 */
@SpringBootApplication
@Slf4j
public class ConsumerConfig {
    private static final Map<MessageQueue, Long> offsetTable = new HashMap<MessageQueue, Long>();


    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.groupName}")
    private String groupName;
    @Value("${rocketmq.consumer.consumeThreadMin}")
    private Integer consumeThreadMin;
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private Integer consumeThreadMax;
    @Value("${rocketmq.consumer.topics}")
    private String topics;
    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
    private Integer consumeMessageBatchMaxSize;

    @Autowired
    private MessageListenerConcurrently messageListenerConcurrently;

    @Bean
    public DefaultMQPushConsumer getRocketMQConsumer() throws RocketMQException {
        if (StringUtils.isEmpty(groupName)) {
            log.error("group name is empty");
            throw new RocketMQException(RocketMQException.MQ_GROUP_NAME_ERROR, "group name is empty");
        }

        if (StringUtils.isEmpty(namesrvAddr)) {
            log.error("namesrv addr is empty");
            throw new RocketMQException(RocketMQException.MQ_ADDR_ERROR, "namesrv addr is empty");
        }


        if (StringUtils.isBlank(topics)) {
            log.info("topic is empty");
            throw new RocketMQException(RocketMQException.MQ_TOPIC_ERROR, "topic is empty");


        }

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        consumer.setConsumerGroup(this.groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setMessageListener(messageListenerConcurrently);
        if (consumeThreadMin != null) {
            consumer.setConsumeThreadMin(consumeThreadMin);
        }

        if (consumeThreadMax != null) {
            consumer.setConsumeThreadMax(consumeThreadMax);
        }

        if (consumeMessageBatchMaxSize != null) {
            consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
        }

        try {
            /**
             * 设置该消费者订阅的主题和tag，如果是订阅该主题下的所有tag，则tag使用*；如果需要指定订阅该主题下的某些tag，则使用||分割，例如tag1||tag2||tag3
             */
            String[] topicTagsArr = topics.split(";");
            for (String topicTags : topicTagsArr) {
                String[] topicTag = topicTags.split(":");
                consumer.subscribe(topicTag[0], topicTag[1]);
            }
            consumer.start();


            log.info("consumer is started !  groupName:{},addr:{} topics:{}", groupName, namesrvAddr, topics);

        } catch (MQClientException e) {
            log.error("consumer start error! e:{}", e);

        }
        return consumer;
    }


    @Bean
    public DefaultMQPullConsumer getMqPullCustomer(){
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("pullConsumer");



        return null;
    }

}
