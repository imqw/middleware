package com.qiuwei.rocketmq.config;

import com.qiuwei.rocketmq.exception.RocketMQException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 消息 提供者配置
 *
 * @Author: qiuwei@19pay.com.cn
 * @Date: 2019-12-16.
 */
@SpringBootApplication
@Slf4j
public class ProducerConfig {


    @Value("${rocketmq.producer.groupName}")
    private String groupName;

    @Value("${rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;

    /**
     * 消息最大长度 默认是4M
     */
    @Value("${rocketmq.producer.maxMessageSize}")
    private Integer maxMessageSize;

    /**
     * 消息超时时长 默认3秒
     */
    @Value("${rocketmq.producer.sendMsgTimeout}")
    private Integer sendMsgTimeout;

    /**
     * 消息重发次数 默认2次
     */
    @Value("${rocketmq.producer.retryTimesWhenSendFailed}")
    private Integer retryTimesWhenSendFailed;


    @Bean
    public DefaultMQProducer getRocketMQProducer() throws RocketMQException {

        if (StringUtils.isEmpty(groupName)) {
            log.error("group name is empty");
            throw new RocketMQException(RocketMQException.MQ_GROUP_NAME_ERROR, "groupName is blank");
        }

        if (StringUtils.isBlank(namesrvAddr)) {
            log.error("namesrv addr is empty");

            throw new RocketMQException(RocketMQException.MQ_GROUP_NAME_ERROR, "namesrv addr is empty");

        }

        DefaultMQProducer defaultMQProducer = new DefaultMQProducer();

        defaultMQProducer.setProducerGroup(this.groupName);
        defaultMQProducer.setNamesrvAddr(namesrvAddr);

        /**
         * 拉取模式必须配置
         *
         * 在RocketMQ 4.5.0及之前，vipChannelEnabled字段值默认为true。在RocketMQ 4.5.1之后，修改为了false。
         * 生产者 在开启VIP通道的情况下，会将请求的broker 端口地址-2，改为请求fastRemotingServer
         * 消费者 消费者拉取消息总是会调用remotingServer，因为PullMessageProcessor只在remotingServer中进行了注册，
         * fastRemotingServer无法处理这个请求，因此并不会修改端口，可参考PullAPIWrapper类。
         *
         */
        defaultMQProducer.setVipChannelEnabled(false);

        //如果需要同一个jvm中不同的producer往不同的mq集群发送消息，需要设置不同的instanceName
        //producer.setInstanceName(instanceName);


        if (maxMessageSize != null) {
            defaultMQProducer.setMaxMessageSize(maxMessageSize);
        }

        if (sendMsgTimeout != null) {
            defaultMQProducer.setSendMsgTimeout(sendMsgTimeout);
        }

        if (retryTimesWhenSendFailed != null) {
            defaultMQProducer.setRetryTimesWhenSendAsyncFailed(retryTimesWhenSendFailed);
        }

        try {
            defaultMQProducer.start();
            log.info("mq producer started  group name : {} , namesrv addr :{}", groupName, namesrvAddr);
        } catch (MQClientException e) {
            log.error("mq start error :{}", e);
            defaultMQProducer.shutdown();
        }

        return defaultMQProducer;

    }

}
