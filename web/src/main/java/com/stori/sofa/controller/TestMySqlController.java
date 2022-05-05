package com.stori.sofa.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.account.common.facade.AccountFacade;
import com.stori.sofa.model.Result;

/**
 * mysql test controller
 * 
 * @author wangkai
 * @date 2022/05/05 18:34
 **/
@RestController
public class TestMySqlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestMySqlController.class);

    @Autowired
    private AccountFacade accountFacade;

    /**
     * mysql test function
     * 
     * @date 2022/05/05 18:35
     * @return java.lang.String
     */
    @GetMapping("/test/mysql")
    public String testMySql() throws IOException {
        Result<String> account = accountFacade.getAccount();
        return account.isSuccess() ? account.getData() : account.getMsg();
    }
}
