package com.example.rabbitmq.fairmq;

import com.example.rabbitmq.utils.MqConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xiaoqiao
 * @version V1.0
 * @ Description  消费者  工作(公平性)队列模式
 * @date 2019/5/25 00:40
 */
public class Consumer1 {

    /**
     * 定义消息队列的名称
     */
    public static final String QUEUE_NAME = "xiaoqiao";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("消费者启动01...");
        //1.创建一个连接
        Connection connection = MqConnectionUtils.newConnection();
        //2.创建一个通道
        Channel channel = connection.createChannel();
        //3.消费者关联队列
        // 第一个参数表示队列名称
        // 第二个参数为是否持久化（true表示是，队列将在服务器重启时生存）
        // 第三个参数为是否是独占队列（创建者可以使用的私有队列，断开后自动删除）
        // 第四个参数为当所有消费者客户端连接断开时是否自动删除队列
        // 第五个参数为队列的其他参数
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //设置公平应答模式  每次从队列中取消息的数量  保证一次只分发一个
        channel.basicQos(1);
        //4.监听获取消息
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("消费者获取生产者消息:" + msg);
                // 模拟 业务逻辑处理时间 场景
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    //手动应答模式  告诉队列服务器 已经处理成功
                    // 如果注释掉手动应答模式 channel.basicAck(envelope.getDeliveryTag(),false);，也就是没有应答  那么消费者只会处理一条消息 然后进行等待 因为没有进行应答
                    //第一个参数：该消息的index   第二个参数：是否批量操作
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }

            }
        };
        //5. 设置应答模式  一个参数 队列名称、第二个参数 应答模式 如果为true 自动应答，false 为手动应答、第三个参数  监听器
        // 自动应答(true)：不在乎消费者对这个消息处理是否成功，都会告诉队列删除该消息。如果处理消息失败的情况下，应该实现自动补偿。
        // 手动应答(false)：当队列把消息推送给消费者，消费者处理完业务逻辑之后，手动返回ack(通知)告诉给队列服务器是否要删除该消息、如果失败，队列服务器做补偿，而不会直接删除该消息、
        channel.basicConsume(QUEUE_NAME, false, defaultConsumer);

        //6.关闭通道和连接 消费者不能关闭 不然消费者无法一直消费消息
//        channel.close();
//        connection.close();

    }

}
