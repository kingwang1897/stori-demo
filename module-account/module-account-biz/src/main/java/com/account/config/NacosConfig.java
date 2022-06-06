package com.account.config;

import org.springframework.context.annotation.Configuration;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;

/***
 * Nacos配置**
 * 
 * @author king
 */
@Configuration
@EnableNacosConfig
@NacosPropertySource(dataId = "stroi-demo", autoRefreshed = true)
public class NacosConfig {

    /**
     * 动态配置
     */
    @NacosValue(value = "${degradeConfig:false}", autoRefreshed = true)
    private String degradeConfig;

    public String getDegradeConfig() {
        return degradeConfig;
    }
}