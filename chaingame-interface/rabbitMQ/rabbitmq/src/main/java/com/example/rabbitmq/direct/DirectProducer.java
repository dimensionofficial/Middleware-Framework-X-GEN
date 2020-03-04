package com.example.rabbitmq.direct;

import com.example.rabbitmq.utils.MqConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xiaoqiao
 * @version V1.0
 * @Description 生产者  交换机类型 Direct   路由模式RoutingKey
 * Direct exchange（直连交换机）是根据消息携带的路由键（routing key）将消息投递给对应队列的
 * 只有当消费者绑定了生产者的routingKey，消费者才可以收到消息。
 * 如果多个消费者都想拿到生产者投递的消息，则消费者需要绑定多个routingKey。
 * @date 2019/5/25 9:14
 */
public class DirectProducer {
    /**
     * 定义交换机名称
     */
    private static final String EXCHANGE_NAME = "my_routingKey_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建mq连接
        Connection connection = MqConnectionUtils.newConnection();
        //2.创建通道
        Channel channel = connection.createChannel();
        //3.生产者绑定交换机  第一个参数：交换机名称  第二个参数:交换机类型
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        //定义路由键 如果定义了路由键，但是没有对应的队列接收的话  消息会丢失
        String routingKey = "msg";
        //4.创建对应的消息
        String msg = "routing_send_msg";
        //5.发送消息 第一个参数：交换机名称 第二个参数：路由键(routingKey) 第三个参数：参数为消息的其他属性 第四个参数：消息内容
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
        System.out.println("生产者投递消息:" + msg);
        //6.关闭通道和连接
        channel.close();
        connection.close();
    }
}
