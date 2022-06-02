package com.account.config;

import org.springframework.context.annotation.Configuration;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;

/**
 * Nacos配置
 * 
 * @author king
 */
@Configuration
@EnableNacosConfig
@NacosPropertySource(dataId = "stroi-demo-account", autoRefreshed = true)
public class NacosConfig {

    /**
     * 数据库密码
     */
    @NacosValue(value = "${sqlPassword:false}", autoRefreshed = true)
    private String sqlPassword;

    public String getSqlPassword() {
        return sqlPassword;
    }
}