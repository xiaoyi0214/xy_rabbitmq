package com.xiaoyi.quickstart.a_quickstart;

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
public class RabbitmqProducter {

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

        // 通过channel发送信息
        for (int i = 0; i < 5; i++) {
            String message = "test---"+i;
            /**
             * 老师以前讲过说我们的消息会发送的exchange上，
             * 但是在这里我们没有指定交换机?那我们的消息发送到哪里了？？？？
             * The default exchange is implicitly bound to every queue, with a routing key equal to the queue name.
             * It is not possible to explicitly bind to, or unbind from the default exchange. It also cannot be deleted.
             * 说明:加入我们消息发送的时候没有指定具体的交换机的话，那么就会发送到rabbimtq指定默认的交换机上，
             * 那么该交换机就会去根据routing_key 查找对应的queueName 然后发送的该队列上.
             *
             */
            channel.basicPublish("", "test-queue-01", null, message.getBytes());
        }

        channel.close();
        connection.close();
    }
}
