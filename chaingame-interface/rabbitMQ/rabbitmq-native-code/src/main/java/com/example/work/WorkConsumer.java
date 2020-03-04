package com.example.work;

import com.example.util.RabbitMqConnection;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xiaoqiao
 * @version V1.0
 * @Description 工作队列消费者
 * 模拟的时候 需要启动二个消费者，只需要修改 port 变量的值即可(1000\3000)，模拟二个不同的消费者线程等待不同的时间实现公平消费的例子
 * @date 2019/12/5 22:13
 */
public class WorkConsumer {
    /**
     * 队列名称
     */
    public static final String QUEUE_NAME = "orderQueue";

    public static int port = 2000;

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("公平消费者启动：" + port);
        //1.创建连接
        Connection connection = RabbitMqConnection.getConnection();
        //2.设置通道
        final Channel channel = connection.createChannel();
        //3.表示 MQ 服务器每次向消费者推送消息的数量，只有消费者手动 ack 应答之后才会继续发送消息
        channel.basicQos(3);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //需要消费的消息内容
                String msg = new String(body, "UTF-8");
                System.out.println("消费者消费消息:" + msg);
                //设置手动应答 ack 模式
                channel.basicAck(envelope.getDeliveryTag(), false);
                try {
                    Thread.sleep(port);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //4.监听队列,消费消息 autoAck:false表示手动应答模式，true表示自动应答模式
        channel.basicConsume(QUEUE_NAME, false, defaultConsumer);
    }
}
