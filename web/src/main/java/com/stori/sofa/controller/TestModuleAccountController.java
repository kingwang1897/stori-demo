package com.stori.sofa.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.account.facade.AccountInternalFacade;
import com.account.model.Result;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * mysql test controller
 * 
 * @author king
 * @date 2022/05/05 18:34
 **/
@RestController
public class TestModuleAccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestModuleAccountController.class);

    @Autowired
    private AccountInternalFacade accountInternalFacade;

    // 因为没有引入依赖，所以打开注释会报错
    // @Autowired
    // private AccountExternalFacade accountExternalFacade;

    @Autowired
    private MeterRegistry registry;

    /**
     * module-account test function
     *
     * @date 2022/05/05 19:25
     * @return java.lang.String
     */
    @GetMapping("/test/module/account")
    public String testModuleAccount() throws IOException {
        registry.counter("TestModuleAccountController.ModuleAccount.count").increment();

        Result<String> accountInternal = accountInternalFacade.getAccount();
        return "accountInternal is: " + accountInternal.getData();
    }

    /**
     * module-account test nacos config function
     *
     * @date 2022/05/23 19:25
     * @return java.lang.String
     */
    @GetMapping("/test/module/account/nacos/config")
    public String testNacosConfig() throws IOException {
        registry.counter("TestModuleAccountController.NacosConfig.count").increment();

        return "accountInternal nacos config is: " + accountInternalFacade.getNacosConfig().getData();
    }
}
