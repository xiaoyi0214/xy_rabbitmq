package com.xiaoyi.advanced_features.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * Created on 2021/2/22.
 *
 * @author 小逸
 * @description
 */
public class DlxProducer {
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
        String exchangeName = "normal-dlx-exchange";
        String routingKey = "normal-dlx-exchange.key1";
        //消息十秒没有被消费，那么就会转到死信队列上
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .expiration("10000")
                .build();
        String messageBody = "dlx message";

        for(int i=0;i<100;i++) {
            channel.basicPublish(exchangeName,routingKey,basicProperties,messageBody.getBytes());
        }
        channel.close();
        connection.close();
    }

}
