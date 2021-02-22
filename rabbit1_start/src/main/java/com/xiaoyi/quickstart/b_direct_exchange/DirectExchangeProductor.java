package com.xiaoyi.quickstart.b_direct_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created on 2021/1/25.
 *
 * @author 小逸
 * @description
 */
public class DirectExchangeProductor {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置工厂属性
        connectionFactory.setHost("101.133.167.247");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("test");
        connectionFactory.setUsername("xiaoyi");
        connectionFactory.setPassword("xiaoyi");

        // 创建连接对象
        Connection connection = connectionFactory.newConnection();

        // 创建channel
        Channel channel = connection.createChannel();


        String exchangeName = "direct-exchange";

        String routingKey = "direct-exchange.key1";

        String messageBody = "direct message";

        channel.basicPublish(exchangeName, routingKey, null, messageBody.getBytes());
        channel.close();
        connection.close();


    }
}
