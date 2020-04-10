package com.qiuwei.websocket.domain;

import lombok.Data;

/**
 * 消息对象
 *
 * @Author: qiuwei@19pay.com.cn
 * @Date: 2019-12-18.
 */
@Data
public class SocketMsg {


    /**
     * 聊天类型0：群聊，1：单聊.
     */
    private int type;
    /**
     * 发送者
     */
    private String fromUser;
    /**
     * 接受者
     */
    private String toUser;
    /**
     * 消息
     */
    private String msg;


}
