package com.xiaoyi.messagedelegate;

import com.xiaoyi.entity.Order;

import java.io.File;
import java.util.Map;

/**
 * Created on 2021/2/22.
 *
 * @author 小逸
 * @description
 */
public class MessageDelegate {
    public void handleMessage(String msgBody) {
        System.out.println("MessageDelegate。。。。。。handleMessage"+msgBody);
    }

    public void consumerMsg(String msg){
        System.out.println("MessageDelegate。。。。。。consumerMsg"+msg);
    }

    public void consumerTopicQueue(String msgBody) {
        System.out.println("MessageDelegate。。。。。。consumerTopicQueue"+msgBody);

    }

    public void consumerTopicQueue2(String msgBody) {
        System.out.println("MessageDelegate。。。。。。consumerTopicQueue2"+msgBody);

    }

    /**
     * 处理json
     * @param jsonMap
     */
    public void consumerJsonMessage(Map jsonMap) {
        System.out.println("MessageDelegate ============================处理json"+jsonMap);
    }

    /**
     * 处理order得
     * @param order
     */
    public void consumerJavaObjMessage(Order order) {
        System.out.println("MessageDelegate ============================处理java对象"+order.toString());

    }



    public void consumerFileMessage(File file) {
        System.out.println("MessageDelegate========================处理文件"+file.getName());
    }
}
