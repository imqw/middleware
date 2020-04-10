package com.qiuwei.websocket.handle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * websocket处理器
 *
 * @Author: qiuwei@19pay.com.cn
 * @Date: 2019-12-18.
 */
@ServerEndpoint("/websocket/{nickname}")
@Component
@Slf4j
public class MyWebSocket {

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();


    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 客户端名称
     */

    private String nickname;

    /**
     * 群发定义消息
     */

    public static void sendInfo(String message) {


        webSocketSet.forEach(myWebSocket -> {

            try {
                myWebSocket.sendMessage(message);
            } catch (Exception e) {
            }

        });
    }

    /**
     * 连接建立成功的调用方法
     */

    @OnOpen
    public void open(Session session, @PathParam("nickname") String nickname) {

        this.session = session;
        this.nickname = nickname;
        webSocketSet.add(this);

        log.info("有新连接加入:{}...当前在线人数:{}", nickname, webSocketSet.size());

        sendInfo("欢迎 "+nickname+" 加入群聊");

        try {
            sendMessage("恭喜您成功连接上WebSocket-->当前在线人数为：" + webSocketSet.size());
        } catch (IOException e) {
            log.error("IO异常");
            e.printStackTrace();
        }
    }


    /**
     * 连接关闭调用的方法
     */

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        log.info("有一连接关闭！当前在线人数为" + webSocketSet.size());
    }


    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("nickname") String nickname) {

        log.info("来自客户端的消息...{}:{}",nickname, message);

        //群发消息
        sendInfo(nickname+": "+message);
    }

    /**
     * 错误时调用
     */

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();

    }


    /**
     * 发送消息
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


}
