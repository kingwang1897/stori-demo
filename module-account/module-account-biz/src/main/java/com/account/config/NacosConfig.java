// package com.account.config;
//
// import org.springframework.context.annotation.Configuration;
//
// import com.alibaba.nacos.api.config.annotation.NacosValue;
// import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
// import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
//
/// **
// * Nacos配置
// *
// * @author chongyangchen
// */
// @Configuration
// @EnableNacosConfig
// @NacosPropertySource(dataId = "stroi-demo", autoRefreshed = true)
// public class NacosConfig {
//
// /**
// * 降级配置
// */
// @NacosValue(value = "${degradeConfig:null}", autoRefreshed = true)
// private String degradeConfig;
//
// /**
// * getter of filed authorizeSwitch
// */
// public String getDegradeConfig() {
// return degradeConfig;
// }
// }