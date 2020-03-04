package com.example.rabbitmq.fairmq;

import com.example.rabbitmq.utils.MqConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xiaoqiao
 * @version V1.0
 * @Description 生产者    工作(公平性)队列模式
 * @date 2019/5/25 00:25
 */
public class Producer {
    /**
     * 队列名称
     */
    private static final String QUEUE_NAME = "xiaoqiao";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建一个新的连接
        Connection connection = MqConnectionUtils.newConnection();
        //2.创建一个通道
        Channel channel = connection.createChannel();
        //3.创建一个队列
        // 第一个参数表示队列名称
        // 第二个参数为是否持久化（true表示是，队列将在服务器重启时生存）
        // 第三个参数为是否是独占队列（创建者可以使用的私有队列，断开后自动删除）
        // 第四个参数为当所有消费者客户端连接断开时是否自动删除队列
        // 第五个参数为队列的其他参数
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //循环发送消息  消费者集群情况
        for (int i = 1; i <= 50; i++) {
            //4.创建msg
            String msg = "我是生产者" + i;
            System.out.println("生产者投递消息内容:" + msg);
            //5.生产者发送消息
            // 第一个参数为交换机名称
            // 第二个参数为队列映射的路由key
            // 第三个参数为消息的其他属性
            // 第四个参数为发送信息的主体
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        }

        //6.关闭通道 和 连接
        channel.close();
        connection.close();
    }
}
