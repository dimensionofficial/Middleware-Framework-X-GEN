package com.example.controller;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: DirectProducer Direct 类型 生产者
 * @Author: xiaoqiao
 * @CreateDate: 2019/12/11 11:38
 * @Version: 1.0
 */
@RequestMapping("/direct")
@RestController
public class DirectProducer {

    /**
     * 交换机名称
     */
    public static String DIRECT_EXCHANGE = "SpringBoot_order_direct_exchange";

    /**
     * Direct 类型 短信路由键
     */
    public static String MSG_DIRECT_ROUTING_KEY = "msg";

    /**
     * Direct 类型 邮件路由键
     */
    public static String EMAIL_DIRECT_ROUTING_KEY = "email";


    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("/sendMsg")
    public String sendMsg() {
        String msg = "Hello";
        amqpTemplate.convertAndSend(DIRECT_EXCHANGE, MSG_DIRECT_ROUTING_KEY, msg);
        return "SUCCESS";
    }

}