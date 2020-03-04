package com.example.rabbitmq.amqp;

import com.example.rabbitmq.utils.MqConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xiaoqiao
 * @version V1.0
 * @Description 生产者  AMQP
 * @date 2019/5/24 23:25
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
        //3.创建一个队列 第一个参数 队列名称、第二个参数 是否持久化
        // 第三个参数 是否排外的，有两个作用：
        // 一：当连接关闭时connection.close()该队列是否会自动删除；
        // 二：该队列是否是私有的private，如果不是排外的，可以使用两个消费者都访问同一个队列，没有任何问题，如果是排外的，会对当前队列加锁，其他通道channel是不能访问的，如果强制访问会报异常
        // 第四个参数：是否自动删除，当最后一个消费者断开连接之后队列是否自动被删除，可以通过RabbitMQ Management，查看某个队列的消费者数量，当consumers = 0时队列就会自动删除
        // 第五个参数: 其它一些参数
        //原文：https://blog.csdn.net/vbirdbest/article/details/78670550
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //添加事务机制
        try {
            //开启事务
            channel.txSelect();
            //4.创建msg
            String msg = "我是生产者";
            System.out.println("生产者投递消息内容:" + msg);
            //5.生产者发送消息
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            //模拟发生错误  事务进行回滚
            int i = 1 / 0;
            //提交事务
            channel.txCommit();
        } catch (Exception e) {
            //回滚事务
            channel.txRollback();
            System.out.println("生产者事务回滚...");
            e.printStackTrace();
        } finally {
            //6.关闭通道 和 连接
            channel.close();
            connection.close();
        }

    }
}
