package com.bill.config;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.bill.model.BillNacosProperties;
import com.google.common.base.Throwables;

/***
 * Nacos配置**
 * 
 * @author king
 */
@Configuration
public class BillNacosConfig implements EnvironmentAware {
    private static final Logger logger = LoggerFactory.getLogger(BillNacosConfig.class);

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean("billNacosProperties")
    public BillNacosProperties initNacosConfig() {
        try {
            BillNacosProperties billNacosProperties = new BillNacosProperties();
            String addr = environment.getProperty("nacos.server-addr").split(":")[0];
            String dataId = environment.getProperty("nacos.server.bill.data.id");
            String group = environment.getProperty("nacos.server.bill.group");

            billNacosProperties.getProperties().put(PropertyKeyConst.SERVER_ADDR, addr);
            ConfigService configService = NacosFactory.createConfigService(billNacosProperties.getProperties());
            String content = configService.getConfig(dataId, group, 5000);
            InputStream stream = new ByteArrayInputStream(content.getBytes(Charset.forName("UTF-8")));
            billNacosProperties.getProperties().load(stream);
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    try {
                        String content = configService.getConfig(dataId, group, 5000);
                        billNacosProperties.getProperties()
                            .load(new ByteArrayInputStream(content.getBytes(Charset.forName("UTF-8"))));
                    } catch (Exception e) {
                        logger.error("receiveConfigInfo error, cause by: {}.", Throwables.getStackTraceAsString(e));
                    }
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });

            return billNacosProperties;
        } catch (Exception e) {
            logger.error("init error, cause by: {}.", Throwables.getStackTraceAsString(e));
            return null;
        }
    }
}