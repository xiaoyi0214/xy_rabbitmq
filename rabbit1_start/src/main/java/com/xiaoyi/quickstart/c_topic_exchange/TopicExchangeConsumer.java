package com.xiaoyi.quickstart.c_topic_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created on 2021/1/25.
 *
 * @author 小逸
 * @description
 */
public class TopicExchangeConsumer {

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

        // exchange
        String exchangeName = "topic-exchange";
        String exchangeType = "topic";
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, null);

        // queue
        String queueName = "topic-queue";
        channel.queueDeclare(queueName, true, false, false, null);

        // bind
        // 单个绑定 == direct
        String routingKey = "topic-exchange.key";
        channel.queueBind(queueName, exchangeName, routingKey);
        // 单个单词匹配
        String routingKey2 = "topic-exchange.*";
        channel.queueBind(queueName, exchangeName, routingKey2);

        // 多个单词匹配
        String routingKey3 = "topic-exchange.#";
        channel.queueBind(queueName, exchangeName, routingKey3);

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        channel.basicConsume(queueName, true
        , queueingConsumer);

        while (true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            System.out.println("接收到消息：" + new String(delivery.getBody()));
        }


    }
}
