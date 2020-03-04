package com.example.topic;

import com.example.util.RabbitMqConnection;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xiaoqiao
 * @version V1.0
 * @Description Topic 类型  邮件消费者
 * email.*  消息路由类型为 email 只能匹配一个词 以 email 开头 例如：email.order; *.email 以 email 结束，匹配单个。例如：order.email
 * email.#  消息路由类型为 email 能匹配多个词 以email 开头   例如：email.order.order1;#.email 以 email 结束，匹配多个。例如：order.order1.email
 * @date 2019/12/5 22:50
 */
public class EmailConsumerTopic {
    /**
     * 邮件队列名称
     */
    public static final String QUEUE_NAME = "order_topic_consumer_email";

    /**
     * 交换机名称
     */
    public static final String EXCHANGE_NAME = "order_topic_ExChange";


    /**
     * routingKey 短信路由键
     */
    public static String ROUTING_KEY_MSG = "msg";

    /**
     * routingKey  邮件路由键
     */
    public static String ROUTING_KEY_EMAIL = "order.#";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println(" Topic 类型 邮件消费者启动...");
        //1.创建连接
        Connection connection = RabbitMqConnection.getConnection();
        //2.设置通道
        final Channel channel = connection.createChannel();
        //3.交换机绑定队列
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY_EMAIL);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //需要消费的消息内容
                String msg = new String(body, "UTF-8");
                System.out.println("邮件消费者消费消息:" + msg);
            }
        };
        //4.监听队列,消费消息 autoAck:false表示手动应答模式，true表示自动应答模式
        channel.basicConsume(QUEUE_NAME, true, defaultConsumer);
    }
}
