package com.xiaoyi.quickstart.e_message;

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
 * Created on 2021/1/25.
 *
 * @author 小逸
 * @description
 */
public class MessageProductor {
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

        // 额外属性
        Map<String,Object> headsMap = new HashMap<>();
        headsMap.put("company","alibaba");
        headsMap.put("name","xiaoyi");
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)//2标识持久化消息  1标识 服务重启后 消息不会被持久化
                .expiration("10000")
                .contentEncoding("utf-8")
                .correlationId(UUID.randomUUID().toString())
                .headers(headsMap)
                .build();

        //5:通过channel发送消息
        for(int i=0;i<5;i++) {
            String message = "hello--"+i;

            channel.basicPublish("direct-exchange","direct-exchange.key",basicProperties,message.getBytes());
        }
    }
}
