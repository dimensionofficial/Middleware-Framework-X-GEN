package com.example.ptp;

import com.example.util.RabbitMqConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xiaoqiao
 * @version V1.0
 * @Description 生产者 投递消息到 orderQueue
 * @date 2019/12/4 20:31
 */
public class Producer {
    public static final String QUEUE_NAME = "orderQueue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        System.out.println("生产者启动...");
        //1.创建连接
        Connection connection = RabbitMqConnection.getConnection();
        //2.设置通道
        Channel channel = connection.createChannel();
        //3.发送消息内容
        String msg = " mq 点对点消息测试";
        System.out.println("生产者投递消息:" + msg);
        //4.发送消息
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        //5.关闭连接
        channel.close();
        connection.close();
    }
}
