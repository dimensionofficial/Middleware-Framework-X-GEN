package com.example.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Description: MqConfig
 * email.*  消息路由类型为 email 只能匹配一个词 以 email 开头 例如：email.order; *.email 以 email 结束，匹配单个。例如：order.email
 * email.#  消息路由类型为 email 能匹配多个词 以email 开头   例如：email.order.order1;#.email 以 email 结束，匹配多个。例如：order.order1.email
 * @Author: xiaoqiao
 * @CreateDate: 2019/12/11 11:09
 * @Version: 1.0
 */
@Component
public class MqConfig {

    /***********************************************   Fanout 类型 ****************************************************/

    /**
     * Fanout 类型 短信队列
     */
    public static String FANOUT_MSG_QUEUE = "order_fanout_msg_queue";

    /**
     * Fanout 类型 邮件队列
     */
    public static String FANOUT_EMAIL_QUEUE = "order_fanout_email_queue";

    /**
     * Fanout 类型 交换机
     */
    public static String FANOUT_EXCHANGE = "SpringBoot_order_fanout_exchange";


    /**
     * Fanout 类型 短信队列
     *
     * @return
     */
    @Bean
    public Queue fanoutMsgQueue() {
        return new Queue(FANOUT_MSG_QUEUE);
    }

    /**
     * Fanout 类型  邮件队列
     *
     * @return
     */
    @Bean
    public Queue fanoutEmailQueue() {
        return new Queue(FANOUT_EMAIL_QUEUE);
    }

    /**
     * Fanout 类型交换机
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    /**
     * 短信队列绑定 Fanout 类型 交换机
     *
     * @return
     */
    @Bean
    public Binding msgBindingFanoutExchange(Queue fanoutMsgQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutMsgQueue).to(fanoutExchange);
    }

    /**
     * 邮件队列绑定 Fanout 类型 交换机
     *
     * @return
     */
    @Bean
    public Binding emailBindingFanoutExchange(Queue fanoutEmailQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutEmailQueue).to(fanoutExchange);
    }


    /************************************************ Direct 类型 *****************************************************/

    /**
     * Direct 类型 短信队列
     */
    public static String DIRECT_MSG_QUEUE = "order_direct_msg_queue";

    /**
     * Direct 类型 邮件队列
     */
    public static String DIRECT_EMAIL_QUEUE = "order_direct_email_queue";

    /**
     * Direct 类型 交换机
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

    /**
     * Direct 类型 短信队列
     *
     * @return
     */
    @Bean
    public Queue directMsgQueue() {
        return new Queue(DIRECT_MSG_QUEUE);
    }

    /**
     * Direct 类型  邮件队列
     *
     * @return
     */
    @Bean
    public Queue directEmailQueue() {
        return new Queue(DIRECT_EMAIL_QUEUE);
    }

    /**
     * Direct 类型交换机
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    /**
     * 短信队列绑定 Direct 类型 交换机
     *
     * @return
     */
    @Bean
    public Binding msgBindingDirectExchange(Queue directMsgQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(directMsgQueue).to(directExchange).with(MSG_DIRECT_ROUTING_KEY);
    }

    /**
     * 邮件队列绑定 Direct 类型 交换机
     *
     * @return
     */
    @Bean
    public Binding emailBindingExchange(Queue directEmailQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(directEmailQueue).to(directExchange).with(EMAIL_DIRECT_ROUTING_KEY);
    }

    /******************************************* Topic 类型 ***********************************************************/

    /**
     * Topic 类型 短信队列
     */
    public static String TOPIC_MSG_QUEUE = "order_topic_msg_queue";

    /**
     * Topic 类型 邮件队列
     */
    public static String TOPIC_EMAIL_QUEUE = "order_topic_email_queue";

    /**
     * Topic 类型 交换机
     */
    public static String TOPIC_EXCHANGE = "SpringBoot_order_topic_exchange";

    /**
     * Topic 类型 短信路由键
     */
    public static String MSG_TOPIC_ROUTING_KEY = "order.#";

    /**
     * Topic 类型 邮件路由键
     */
    public static String EMAIL_TOPIC_ROUTING_KEY = "order.#";

    /**
     * Topic 类型 短信队列
     *
     * @return
     */
    @Bean
    public Queue topicMsgQueue() {
        return new Queue(TOPIC_MSG_QUEUE);
    }

    /**
     * Topic 类型  邮件队列
     *
     * @return
     */
    @Bean
    public Queue topicEmailQueue() {
        return new Queue(TOPIC_EMAIL_QUEUE);
    }


    /**
     * Topic 类型交换机
     *
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }


    /**
     * 短信队列绑定 Topic 类型 交换机
     *
     * @return
     */
    @Bean
    public Binding msgBindingTopicExchange(Queue topicMsgQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicMsgQueue).to(topicExchange).with(MSG_TOPIC_ROUTING_KEY);
    }

    /**
     * 邮件队列绑定 Topic 类型 交换机
     *
     * @return
     */
    @Bean
    public Binding emailBindingTopicExchange(Queue topicEmailQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicEmailQueue).to(topicExchange).with(EMAIL_TOPIC_ROUTING_KEY);
    }
}