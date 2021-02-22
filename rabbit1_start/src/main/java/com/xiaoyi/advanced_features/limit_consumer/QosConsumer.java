package com.xiaoyi.advanced_features.limit_consumer;

import com.rabbitmq.client.*;
import com.xiaoyi.advanced_features.ack_nack.AckConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created on 2021/2/22.
 *
 * @author 小逸
 * @description
 */
public class QosConsumer {
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
        String exchangeName = "qos-direct-exchange";
        String exchangeType = "direct";
        String queueName = "direct-queue";
        String routingKey = "qos-direct-exchange.key1";


        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);

        channel.queueDeclare(queueName,true,false,false,null);

        channel.queueBind(queueName,exchangeName,routingKey);


        /**
         * 限流设置:  prefetchSize：每条消息大小的设置
         * prefetchCount:标识每次推送多少条消息 一般是一条
         * global:false标识channel级别的  true:标识消费的级别的
         */
        channel.basicQos(0,5,false);

        /**
         * 消费端限流 需要关闭消息自动签收
         */
        channel.basicConsume(queueName, false, new QosConsumers(channel));
    }
}
