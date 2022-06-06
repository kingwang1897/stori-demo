package com.stori.sofa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "spring.rocketmq")
@Data
public class MqProperties {

    private String namesrvAddr;

    private String topic;

    private String group;

    private String tags;

}
