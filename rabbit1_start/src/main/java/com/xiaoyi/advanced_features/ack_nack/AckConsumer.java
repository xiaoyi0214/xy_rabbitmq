package com.xiaoyi.advanced_features.ack_nack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * Created on 2021/2/22.
 *
 * @author 小逸
 * @description
 */
public class AckConsumer extends DefaultConsumer {

    private Channel channel;

    public AckConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        try{
            //模拟业务
            Integer mark = (Integer) properties.getHeaders().get("mark");
            if(mark != 0 ) {
                System.out.println("消费消息:"+new String(body));
                // 自动签收
                channel.basicAck(envelope.getDeliveryTag(),false);
            }else{
                throw new RuntimeException("模拟业务异常");
            }
        }catch (Exception e) {
            System.out.println("异常消费消息:"+new String(body));
            //拒收 重回队列
            //channel.basicNack(envelope.getDeliveryTag(),false,true);
            //拒收 不重回队列
            channel.basicNack(envelope.getDeliveryTag(),false,false);

        }
    }
}
