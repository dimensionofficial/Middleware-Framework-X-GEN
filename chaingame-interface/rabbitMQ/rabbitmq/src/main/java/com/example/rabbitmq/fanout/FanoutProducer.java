package com.example.rabbitmq.fanout;

import com.example.rabbitmq.utils.MqConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xiaoqiao
 * @version V1.0
 * @Description 生产者  交换机类型 Fanout   发布订阅模式
 *   Fanout exchange（扇型交换机）将消息路由给绑定到它的所有队列
 *   这时候当邮件队列和短息队列都绑定了Fanout类型的交换机的时候，
 *    当生产者投递消息的时候，交换机会将消息分别转发给绑定它的所有队列，这时候邮件队列和短信队列队可以收到生产者投递的消息。
 * @date 2019/5/25 9:14
 */
public class FanoutProducer {
    /**
     * 定义交换机名称
     */
    private static final String EXCHANGE_NAME = "my_fanout_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建mq连接
        Connection connection = MqConnectionUtils.newConnection();
        //2.创建通道
        Channel channel = connection.createChannel();
        //3.生产者绑定交换机  第一个参数：交换机名称  第二个参数:交换机类型
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //4.创建对应的消息
        String msg = "发布订阅模式：fanout_send_msg";
        //5.发送消息 第一个参数：交换机名称 第二个参数：routingKey 第三个参数：参数为消息的其他属性  第四个参数：消息内容
        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
        System.out.println("生产者投递消息:" + msg);
        //6.关闭通道和连接
        channel.close();
        connection.close();
    }
}
