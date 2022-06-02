package com.account.model;

import java.util.Properties;

import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.lang.Nullable;

/**
 * Nacos配置
 *
 * @author king
 */
public class AccountNacosProperties {

    /**
     * 系统配置（列表）
     */
    private Properties properties = new Properties();

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public <T> T getProperty(String key, Class<T> targetValueType) {
        Object value = properties.getProperty(key);
        if (value == null) {
            return null;
        }

        return convertValueIfNecessary(value, targetValueType);
    }

    protected <T> T convertValueIfNecessary(Object value, @Nullable Class<T> targetType) {
        if (targetType == null) {
            return (T)value;
        }

        return DefaultConversionService.getSharedInstance().convert(value, targetType);
    }
}