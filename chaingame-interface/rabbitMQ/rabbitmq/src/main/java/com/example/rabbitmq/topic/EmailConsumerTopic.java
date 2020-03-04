package com.example.rabbitmq.topic;

import com.example.rabbitmq.utils.MqConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xiaoqiao
 * @version V1.0
 * @Description 邮件消费者  交换机类型 Topic   路由模式RoutingKey
 *  *   Topic exchange（主题交换机）队列通过路由键绑定到交换机上，然后，交换机根据消息里的路由值，将消息路由给一个或多个绑定队列
 *  *   符号#：匹配一个或者多个词log.# 可以匹配log.msg、log.msg.msg1或者log.email、log.email.email1
 *  *   符号*：只能匹配一个词log.* 可以匹配log.msg或者log.email
 * @date 2019/5/25 10:30
 */
public class EmailConsumerTopic {
    /**
     * 定义队列名称
     */
    private static final String EMAIL_QUEUE = "email_queue_topic";
    /**
     * 定义交换机名称
     */
    private static final String EXCHANGE_NAME = "my_topic_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("邮件消费者启动...");
        //1.创建mq连接
        Connection connection = MqConnectionUtils.newConnection();
        //2.创建通道
        Channel channel = connection.createChannel();
        //3.消费者声明队列
        // 第一个参数表示队列名称
        // 第二个参数为是否持久化（true表示是，队列将在服务器重启时生存）
        // 第三个参数为是否是独占队列（创建者可以使用的私有队列，断开后自动删除）
        // 第四个参数为当所有消费者客户端连接断开时是否自动删除队列
        // 第五个参数为队列的其他参数
        channel.queueDeclare(EMAIL_QUEUE, false, false, false, null);
        //4.消费者队列绑定交换机  第一个参数：队列名称  第二个参数：交换机名称  第三个参数：路由键名称
        channel.queueBind(EMAIL_QUEUE, EXCHANGE_NAME, "log.#");
        //5.消费者监听消息
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("邮件消费者获取生产者消息:" + msg);
            }
        };
        //6. 设置应答模式 一个参数 队列名称、第二个参数 应答模式 如果为true 自动应答，false 为手动应答、第三个参数 监听器
        // 自动应答(true)：不在乎消费者对这个消息处理是否成功，都会告诉队列删除该消息。如果处理消息失败的情况下，应该实现自动补偿。
        // 手动应答(false)：当队列把消息推送给消费者，消费者处理完业务逻辑之后，手动返回ack(通知)告诉给队列服务器是否要删除该消息、如果失败，队列服务器做补偿，而不会直接删除该消息、
        channel.basicConsume(EMAIL_QUEUE, true, defaultConsumer);
    }

}
