package com.example.direct;

import com.example.util.RabbitMqConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xiaoqiao
 * @version V1.0
 * @Description Direct 类型  生产者
 *
 * @date 2019/12/5 22:43
 */
public class ProducerDirect {
    /**
     * 交换机名称
     */
    public static final String EXCHANGE_NAME = "order_direct_ExChange";

    /**
     * routingKey 短信路由键
     */
    public static String ROUTING_KEY_MSG = "msg";

    /**
     * routingKey  邮件路由键
     */
    public static String ROUTING_KEY_EMAIL = "email";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("Direct 类型 生产者启动...");
        //1.创建连接
        Connection connection = RabbitMqConnection.getConnection();
        //2.设置通道
        Channel channel = connection.createChannel();
        //3.发送消息内容
        String msg = "order_direct_ExChange 发送消息测试";
        System.out.println("生产者投递消息:" + msg);
        //4.生产者绑定交换机 指定持久化
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
        //5.发送消息
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY_EMAIL, null, msg.getBytes());
        //6.关闭连接
        channel.close();
        connection.close();
    }
}
