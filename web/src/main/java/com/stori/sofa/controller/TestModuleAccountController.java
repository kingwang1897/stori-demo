package com.stori.sofa.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.account.facade.AccountExternalFacade;
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
    private AccountExternalFacade accountExternalFacade;

    // 因为没有引入依赖，所以打开注释会报错
    // @Autowired
    // private AccountInternalFacade accountInternalFacade;

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

        Result<String> accountInternal = accountExternalFacade.getAccount();
        return "accountInternal is: " + accountInternal.getData();
    }
}
