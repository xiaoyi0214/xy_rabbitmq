package com.xiaoyi.advanced_features.comfirm_listener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Date;
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
public class ConfirmListenerProducer {
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
        //设置消息投递模式(确认模式)
        channel.confirmSelect();

        String exchangeName = "confirm-direct-exchange";

        String routingKey = "confirm-direct-exchange.key1";

        //设置消息属性
        Map<String,Object> info = new HashMap<>();
        info.put("company","alibaba");
        info.put("location","hangzhou");

        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .correlationId(UUID.randomUUID().toString())
                .timestamp(new Date())
                .headers(info)
                .build();


        /**
         * 消息确认监听
         */
        channel.addConfirmListener(new MyConfirmListener());
        for (int i=0;i<1;i++) {
            String msgContext = "你好 confirm...."+i;
            channel.basicPublish(exchangeName,routingKey,basicProperties,msgContext.getBytes());
        }


        /**
         * 注意:在这里千万不能调用channel.close不然 消费就不能接受确认了
         */
    }
}
