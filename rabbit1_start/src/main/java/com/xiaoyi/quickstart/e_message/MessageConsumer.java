package com.xiaoyi.quickstart.e_message;

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
public class MessageConsumer {
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
        String exchangeName = "direct-exchange";
        String exchangeType = "direct";
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, null);

        // queue
        String queueName = "direct-queue";
        channel.queueDeclare(queueName, true, false, false, null);

        // 广播模式，与绑定关系无关
        String routingKey = "direct-exchange.key";
        channel.queueBind(queueName, exchangeName, routingKey);

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        channel.basicConsume(queueName, true, queueingConsumer);

        while (true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String reserveMsg = new String(delivery.getBody());
            System.out.println("reserveMsg:"+reserveMsg);
            System.out.println("encoding:"+delivery.getProperties().getContentEncoding());
            System.out.println("company:"+delivery.getProperties().getHeaders().get("company"));
            System.out.println("correlationId:"+delivery.getProperties().getCorrelationId());
        }
    }
}
