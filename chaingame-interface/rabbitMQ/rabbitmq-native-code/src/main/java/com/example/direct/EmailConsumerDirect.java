package com.example.direct;

import com.example.util.RabbitMqConnection;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xiaoqiao
 * @version V1.0
 * @Description 发布订阅 Direct 类型  邮件消费者
 * 默认情况下 邮件消费者只能收到生产者投递的 email 类型的消息，如果邮件消费者也想要收到 msg 类型的消息，需要再新增绑定 msg 路由键即可。
 * 绑定的例子：
 * channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY_MSG);     // 绑定 msg 类型
 * channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY_EMAIL);   // 绑定 email 类型
 * @date 2019/12/5 22:50
 */
public class EmailConsumerDirect {
    /**
     * 邮件队列名称
     */
    public static final String QUEUE_NAME = "order_direct_consumer_email";

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
        System.out.println("Direct 类型 邮件消费者启动...");
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
