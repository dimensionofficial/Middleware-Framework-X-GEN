package com.example.ptp;

import com.example.util.RabbitMqConnection;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xiaoqiao
 * @version V1.0
 * @Description 消费者 从 orderQueue 获取消息进行消费
 * @date 2019/12/4 21:53
 */
public class Consumer {

    public static final String QUEUE_NAME = "orderQueue";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("消费者启动...");
        //1.创建连接
        Connection connection = RabbitMqConnection.getConnection();
        //2.设置通道
        Channel channel = connection.createChannel();
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //需要消费的消息内容
                String msg = new String(body, "UTF-8");
                System.out.println("消费者获取消息:" + msg);
            }
        };
        //3.监听队列
        channel.basicConsume(QUEUE_NAME, true, defaultConsumer);
    }
}
