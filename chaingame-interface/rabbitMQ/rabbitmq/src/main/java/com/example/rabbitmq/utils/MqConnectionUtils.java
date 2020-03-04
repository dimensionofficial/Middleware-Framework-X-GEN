package com.example.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xiaoqiao
 * @version V1.0
 * @Description ptpmq 连接通用类
 * @date 2019/5/24 23:17
 */
public class MqConnectionUtils {
    /**
     * 创建 MQ 连接工厂
     *
     * @return
     */
    public static Connection newConnection() throws IOException, TimeoutException {
        //1.创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //2.设置连接地址
        connectionFactory.setHost("127.0.0.1");
        //3.设置用户名 ptpmq 中创建的用户名
        connectionFactory.setUsername("ming");
        //4. 设置用户密码
        connectionFactory.setPassword("ming");
        //5.设置amqp 端口号  在 ptpmq 服务器上面可以查看
        connectionFactory.setPort(5672);
        //6.设置 virtual host  在 ptpmq 中配置的
        connectionFactory.setVirtualHost("/member");
        Connection connection = connectionFactory.newConnection();
        return connection;
    }
}
