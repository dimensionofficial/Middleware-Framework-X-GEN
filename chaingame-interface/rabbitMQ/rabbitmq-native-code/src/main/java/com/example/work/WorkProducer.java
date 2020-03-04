package com.example.work;

import com.example.util.RabbitMqConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xiaoqiao
 * @version V1.0
 * @Description 工作队列 生产者
 * @date 2019/12/5 22:13
 */
public class WorkProducer {

    /**
     * 队列名称
     */
    public static final String QUEUE_NAME = "orderQueue";

    /**
     * 循环次数
     */
    public static int count = 20;

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        System.out.println("公平队列生产者启动...");
        //1.创建连接
        Connection connection = RabbitMqConnection.getConnection();
        //2.设置通道
        Channel channel = connection.createChannel();
        //3.开启消息确认机制
        channel.confirmSelect();
        //4.发送消息内容
        String msg = "workQueue 发送消息测试";
        System.out.println("生产者投递消息:" + msg);

        for (int i = 0; i < count; i++) {
            //5.发送消息
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        }
        //6.判断消息是否投递成功
        if (channel.waitForConfirms()) {
            System.out.println("生产者投递消息成功，msg:" + msg);
        }
        //5.关闭连接
        channel.close();
        connection.close();
    }
}
