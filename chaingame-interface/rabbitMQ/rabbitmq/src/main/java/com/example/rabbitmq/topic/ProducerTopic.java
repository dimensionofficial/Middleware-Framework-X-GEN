package com.example.rabbitmq.topic;

import com.example.rabbitmq.utils.MqConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xiaoqiao
 * @version V1.0
 * @Description 生产者  交换机类型 Topic   路由模式RoutingKey
 *  *   Topic exchange（主题交换机）队列通过路由键绑定到交换机上，然后，交换机根据消息里的路由值，将消息路由给一个或多个绑定队列
 *  *   符号#：匹配一个或者多个词log.# 可以匹配log.msg、log.msg.msg1或者log.email、log.email.email1
 *  *   符号*：只能匹配一个词log.* 可以匹配log.msg或者log.email
 *
 *
 * @date 2019/5/25 10:14
 */
public class ProducerTopic {
    /**
     * 定义交换机名称
     */
    private static final String EXCHANGE_NAME = "my_topic_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建mq连接
        Connection connection = MqConnectionUtils.newConnection();
        //2.创建通道
        Channel channel = connection.createChannel();
        //3.生产者绑定交换机  第一个参数：交换机名称  第二个参数:交换机类型
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        // 定义routingKey 只能 投递到短信队列
        String routingKey="log.msg.test";
        //4.创建对应的消息
        String msg = "通配符模式：topic_send_msg";
        //5.发送消息 第一个参数：交换机名称 第二个参数：路由键(routingKey) 第三个参数：消息的其他属性 第四个参数：消息内容
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
        System.out.println("生产者投递消息:" + msg);
        //6.关闭通道和连接
        channel.close();
        connection.close();
    }
}
