package com.stori.sofa.message.consumer;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import com.stori.sofa.config.MqProperties;

/**
 * 默认消息监听器
 */
@Configuration
public class ConsumerManager implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerManager.class);

    private ApplicationContext applicationContext;

    @Autowired
    private MqProperties mqProperties;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 开启消息监听
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            LOGGER.info("开启消息监听,group:" + mqProperties.getGroup());
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(mqProperties.getGroup());
            consumer.setNamesrvAddr(mqProperties.getNamesrvAddr());
            consumer.subscribe(mqProperties.getTopic(), mqProperties.getTags());

            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                    ConsumeConcurrentlyContext context) {
                    for (MessageExt messageExt : msgs) {
                        LOGGER.info("ConsumerManager MQ and MQ is:{}.", new String(messageExt.getBody()));
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

            consumer.start();
        } catch (MQClientException e) {
            LOGGER.error("consumer error", e);
        }
    }
}
