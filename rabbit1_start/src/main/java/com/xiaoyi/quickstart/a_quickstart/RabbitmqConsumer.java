package com.xiaoyi.quickstart.a_quickstart;

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
public class RabbitmqConsumer {

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

        String queueName = "test-queue-01";
        /**
         * queue:队列的名称
         * durable:是否持久化, 队列的声明默认是存放到内存中的，如果rabbitmq重启会丢失，如果想重启之后还存在就要使队列持久化，
         *          保存到Erlang自带的Mnesia数据库中，当rabbitmq重启之后会读取该数据库
         * exclusive:连接是否独有，若独有只能自己访问，当连接关闭时connection.close()该队列是否会自动删除；
         *          一般等于true的话用于一个队列只能有一个消费者来消费的场景
         * autodelete:是否自动删除，当最后一个消费者断开连接之后队列是否自动被删除，可以通过RabbitMQ Management，
         *           查看某个队列的消费者数量，当consumers = 0时队列就会自动删除
         *
         */
        // 声明队列
        channel.queueDeclare(queueName, true, true, false, null);

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true,queueingConsumer);
        while (true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();

            String reserveMsg = new String(delivery.getBody());
            System.out.println("消费消息："+reserveMsg);
        }


    }
}
