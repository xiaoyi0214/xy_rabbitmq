package com.xiaoyi.advanced_features.comfirm_listener;

import com.rabbitmq.client.ConfirmListener;

import java.io.IOException;

/**
 * Created on 2021/2/22.
 *
 * @author 小逸
 * @description rabbitMq 消息投递模式
 */
public class MyConfirmListener implements ConfirmListener {
    /**
     *
     * @param deliveryTag 唯一消息Id
     * @param multiple:是否批量
     * @throws IOException
     */
    @Override
    public void handleAck(long deliveryTag, boolean multiple) throws IOException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前时间:"+System.currentTimeMillis()+"  ConfirmListener handleAck:"+deliveryTag);
    }

    /**
     * 处理异常
     * @param deliveryTag
     * @param multiple
     * @throws IOException
     */
    @Override
    public void handleNack(long deliveryTag, boolean multiple) throws IOException {
        System.out.println("ConfirmListener handleNack:"+deliveryTag);

    }
}
