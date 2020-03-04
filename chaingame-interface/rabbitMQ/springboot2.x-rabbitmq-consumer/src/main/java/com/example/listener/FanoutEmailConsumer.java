package com.example.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description: Fanout 类型  邮件消费者
 * 说明：邮件消费者和短信消费者同时监听 order_msg_queue 同一个队列，轮询进行消费
 * @Author: xiaoqiao
 * @CreateDate: 2019/12/11 11:44
 * @Version: 1.0
 */
@Component
@RabbitListener(queues = "order_fanout_email_queue")
public class FanoutEmailConsumer {

    @RabbitHandler
    public void process(String msg) {
        System.out.println("邮件消费者消费消息:" + msg);
    }
}