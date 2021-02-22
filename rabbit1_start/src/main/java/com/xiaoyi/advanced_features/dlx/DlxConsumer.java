package com.xiaoyi.advanced_features.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xiaoyi.advanced_features.ack_nack.AckConsumer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created on 2021/2/22.
 *
 * @author 小逸
 * @description
 */
public class DlxConsumer {
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
        //声明正常的队列
        String nomalExchangeName = "normal-dlx-exchange";
        String exchangeType = "topic";
        String nomalqueueName = "normal-dlx-queue";
        String routingKey = "normal-dlx-exchange.#";
        channel.exchangeDeclare(nomalExchangeName,exchangeType,true,false,null);

        //申明死信队列
        String dlxExhcangeName = "dlx-exchange";
        String dlxQueueName = "dlx-queue";


        Map<String,Object> queueArgs = new HashMap<>();
        //正常队列上绑定死信队列
        queueArgs.put("x-dead-letter-exchange",dlxExhcangeName);
        queueArgs.put("x-max-length",4);
        channel.queueDeclare(nomalqueueName,true,false,false,queueArgs);
        channel.queueBind(nomalqueueName,nomalExchangeName,routingKey);

        //声明死信队列
        channel.exchangeDeclare(dlxExhcangeName,exchangeType,true,false,null);
        channel.queueDeclare(dlxQueueName,true,false,false,null);
        channel.queueBind(dlxQueueName,dlxExhcangeName,"#");

        channel.basicConsume(nomalqueueName,false,new DlxConsumers(channel));
    }
}
