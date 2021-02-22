package com.xiaoyi.quickstart.b_direct_exchange;

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
public class DirectExchangeConsumer {
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

        String exchangeName = "direct-exchange";
        String exchangeType = "direct";
        String queueName = "direct-queue";
        String routingKey = "direct-exchange.key1";

        /**
         * 声明一个交换机
         * exchange:交换机的名称
         * type:交换机的类型 常见的有direct,fanout,topic等
         * durable:设置是否持久化。durable设置为true时表示持久化，反之非持久化.持久化可以将交换器存入磁盘，在服务器重启的时候不会丢失相关信息
         * autodelete:设置是否自动删除。autoDelete设置为true时，则表示自动删除。自动删除的前提是至少有一个队列或者交换器与这个交换器绑定，之后，所有与这个交换器绑定的队列或者交换器都与此解绑。
         * 不能错误的理解—当与此交换器连接的客户端都断开连接时，RabbitMq会自动删除本交换器
         * arguments:其它一些结构化的参数，比如：alternate-exchange
         */
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);

        /**
         * 声明一个队列
         * durable:表示rabbitmq关闭删除队列
         * autodelete:表示没有程序和队列建立连接 那么就会自动删除队列
         * exclusive:有且只有一个消费者监听,服务停止的时候删除该队列
         *
         */
        channel.queueDeclare(queueName,true,false,false,null);

        /**
         * 队列和交换机绑定
         */
        channel.queueBind(queueName,exchangeName,routingKey);


        /**
         * 创建一个消费者
         */
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        /**
         * 开始消费
         */
        channel.basicConsume(queueName,true,queueingConsumer);

        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String reciverMessage = new String(delivery.getBody());
            System.out.println("消费消息:-----"+reciverMessage);
        }

    }
}
