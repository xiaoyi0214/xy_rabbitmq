package com.xiaoyi.advanced_features.ack_nack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created on 2021/2/22.
 *
 * @author 小逸
 * @description
 */
public class AckNackConsumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
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
        String exchangeName = "ack-direct-exchange";
        String exchangeType = "direct";
        String queueName = "direct-queue";
        String routingKey = "ack-direct-exchange.key1";


        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);

        channel.queueDeclare(queueName,true,false,false,null);

        channel.queueBind(queueName,exchangeName,routingKey);


        /**
         * 消费端限流 需要关闭消息自动签收
         */
        channel.basicConsume(queueName,false,new AckConsumer(channel));

    }
}
