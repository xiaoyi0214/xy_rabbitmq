package com.xiaoyi.advanced_features.limit_consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * Created by smlz on 2019/9/30.
 */
public class QosConsumers extends DefaultConsumer {

    private Channel channel;

    public QosConsumers(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body)
            throws IOException
    {
        System.out.println("consumerTag:"+consumerTag);
        System.out.println("envelope:"+envelope);
        System.out.println("properties:"+properties);
        System.out.println("body:"+new String(body));

        /**
         * multiple:false标识不批量签收
         */
//        channel.basicAck(envelope.getDeliveryTag(),false);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
