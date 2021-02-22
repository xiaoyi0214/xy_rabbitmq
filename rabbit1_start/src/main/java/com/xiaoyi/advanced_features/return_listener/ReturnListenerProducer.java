package com.xiaoyi.advanced_features.return_listener;

import com.rabbitmq.client.*;
import com.xiaoyi.advanced_features.comfirm_listener.MyConfirmListener;

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
public class ReturnListenerProducer {
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

        String exchangeName = "return-direct-exchange";

        String routingKey = "return-direct-exchange.key1";

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
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("replyCode:"+replyCode);
                System.out.println("replyText:"+replyText);
                System.out.println("exchange:"+exchange);
                System.out.println("routingKey:"+routingKey);
                System.out.println("properties:"+properties);
                System.out.println("msg body:"+new String(body));
            }
        });
        for (int i=0;i<1;i++) {
            String msgContext = "你好 confirm...."+i;
            channel.basicPublish(exchangeName,routingKey,true,basicProperties,msgContext.getBytes());
        }
    }

}
