package com.stori.sofa.message.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.stori.sofa.config.MqProperties;

/**
 * 消息发送服务实现类
 */
@Component
public class ProducerManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerManager.class);

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Autowired
    private MqProperties mqProperties;

    /**
     * sendMessage
     * 
     * @param msg
     * @return boolean
     */
    public boolean sendMessage(String msg) {
        LOGGER.info("开始发送, msg:{}", msg);
        SendResult sendResult;
        try {
            Message message = new Message(mqProperties.getTopic(), mqProperties.getTags(),
                msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
            sendResult = defaultMQProducer.send(message, 10000);
        } catch (Exception e) {
            LOGGER.error("消息发送失败, msg:{}, cause:{}", msg, e);
            return false;
        }

        if (SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            LOGGER.info("发送成功, sendResult:{}", JSON.toJSONString(sendResult));
            return true;
        }

        LOGGER.info("发送失败, sendResult:{}", JSON.toJSONString(sendResult));
        return false;
    }
}
