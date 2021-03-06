package com.example.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description: Topic 类型  短信消费者
 * @Author: xiaoqiao
 * @CreateDate: 2019/12/11 11:44
 * @Version: 1.0
 */
@Component
@RabbitListener(queues = "order_topic_msg_queue")
public class TopicMsgConsumer {

    @RabbitHandler
    public void process(String msg) {
        System.out.println(" Topic 类型 短信消费者消费消息:" + msg);
    }
}