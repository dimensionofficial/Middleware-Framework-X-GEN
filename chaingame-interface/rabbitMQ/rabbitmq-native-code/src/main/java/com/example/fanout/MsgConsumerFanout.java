package com.example.fanout;

import com.example.util.RabbitMqConnection;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xiaoqiao
 * @version V1.0
 * @Description 发布订阅 Fanout 类型  短信消费者
 * @date 2019/12/5 22:50
 */
public class MsgConsumerFanout {
    /**
     * 消息队列名称
     */
    public static final String QUEUE_NAME = "order_fanout_consumer_msg";

    /**
     * 交换机名称
     */
    public static final String EXCHANGE_NAME = "order_fanout_ExChange";


    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("Fanout类型 短信消费者启动...");
        //1.创建连接
        Connection connection = RabbitMqConnection.getConnection();
        //2.设置通道
        final Channel channel = connection.createChannel();
        //3.交换机绑定队列
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //需要消费的消息内容
                String msg = new String(body, "UTF-8");
                System.out.println("短信消费者消费消息:" + msg);
            }
        };
        //4.监听队列,消费消息 autoAck:false表示手动应答模式，true表示自动应答模式
        channel.basicConsume(QUEUE_NAME, true, defaultConsumer);
    }
}
