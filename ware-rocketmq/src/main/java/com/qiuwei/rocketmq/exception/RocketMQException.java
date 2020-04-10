package com.qiuwei.rocketmq.exception;

/**
 *
 * 消息异常类
 *
 * @Author: qiuwei@19pay.com.cn
 * @Date: 2019-12-16.
 */

public class RocketMQException extends Throwable {

    public final static String MQ_GROUP_NAME_ERROR="mq-001";

    public final static String MQ_ADDR_ERROR="mq-002";

    public final static String MQ_TOPIC_ERROR="mq-003";

    private String code;

    private String msg;

    public RocketMQException() {
    }

    public RocketMQException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RocketMQException(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
