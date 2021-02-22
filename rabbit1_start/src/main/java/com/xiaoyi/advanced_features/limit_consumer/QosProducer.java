package com.xiaoyi.advanced_features.limit_consumer;

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
public class QosProducer {
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
        String exchangeName = "qos-direct-exchange";

        String routingKey = "qos-direct-exchange.key1";

        String messageBody = "ack-direct-exchange message";

        for (int i = 0; i < 1000; i++) {
            Map<String, Object> infoMap = new HashMap<>();
            infoMap.put("mark", i);
            AMQP.BasicProperties builder = new AMQP.BasicProperties().builder()
                    .deliveryMode(2) //消息持久化
                    .contentEncoding("UTF-8")
                    .correlationId(UUID.randomUUID().toString())
                    .headers(infoMap)
                    .build();
            channel.basicPublish(exchangeName, routingKey, builder, (messageBody+i).getBytes());

        }
        channel.close();
        connection.close();
    }
}
