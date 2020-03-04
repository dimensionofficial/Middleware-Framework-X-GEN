package com.example.controller;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: FanoutProducer Fanout 类型 生产者
 * @Author: xiaoqiao
 * @CreateDate: 2019/12/11 11:38
 * @Version: 1.0
 */
@RequestMapping("/fanout")
@RestController
public class FanoutProducer {

    /**
     * Fanout 类型 交换机
     */
    public static String FANOUT_EXCHANGE = "SpringBoot_order_fanout_exchange";

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("/sendMsg")
    public String sendMsg() {
        String msg = "Hello";
        amqpTemplate.convertAndSend(FANOUT_EXCHANGE, "", msg);
        return "SUCCESS";
    }

}