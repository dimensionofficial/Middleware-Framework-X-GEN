package com.example.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xiaoqiao
 * @version V1.0
 * @Description mq 连接方法
 * @date 2019/12/4 20:25
 */
public class RabbitMqConnection {

    public static final int PORT = 5672;
    public static final String IP = "127.0.0.1";
    public static final String USER_NAME = "ming";
    public static final String PASS_WORD = "ming";
    public static final String VIRTUAL_HOST = "/order";

    /**
     * 创建 mq 连接
     *
     * @return
     */
    public static Connection getConnection() throws IOException, TimeoutException {
        //1.创建连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //2.设置连接地址
        connectionFactory.setHost(IP);
        //3.设置连接端口号
        connectionFactory.setPort(PORT);
        //4.设置账号和密码
        connectionFactory.setUsername(USER_NAME);
        connectionFactory.setPassword(PASS_WORD);
        //5.设置 virtualHost
        connectionFactory.setVirtualHost(VIRTUAL_HOST);
        return connectionFactory.newConnection();
    }
}
