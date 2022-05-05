package com.stori.sofa.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.account.common.facade.AccountFacade;

@RestController
public class TestMySqlController {

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
