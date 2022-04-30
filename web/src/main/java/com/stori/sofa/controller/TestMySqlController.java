package com.stori.sofa.controller;

import com.account.common.facade.AccountFacade;
import com.stori.sofa.WebApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TestMySqlController {

    // init the logger
    private static final Logger LOGGER = LoggerFactory.getLogger(TestMySqlController.class);

    @Autowired
    private AccountFacade accountFacade;

    /**
     * testMySql
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/test/mysql")
    public String testMySql() throws IOException {

        return accountFacade.getAccount();
    }
}
