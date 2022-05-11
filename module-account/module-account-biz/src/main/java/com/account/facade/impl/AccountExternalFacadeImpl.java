package com.account.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.account.dal.dao.Account;
import com.account.dal.mapper.AccountMapper;
import com.account.facade.AccountExternalFacade;
import com.account.model.Result;
import com.account.service.ModuleService;
import com.alibaba.fastjson.JSON;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * External Facade Implement
 *
 * @author king
 * @date 2022/05/07 13:54
 **/
public class AccountExternalFacadeImpl implements AccountExternalFacade {
    private static final Logger logger = LoggerFactory.getLogger(AccountExternalFacadeImpl.class);

    private String message;

    public AccountExternalFacadeImpl(String message) {
        this.message = message;
    }

    public AccountExternalFacadeImpl() {}

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private MeterRegistry registry;

    /**
     * getAccount
     *
     * @date 2022/05/07 13:58
     * @return com.stori.sofa.model.Result<java.lang.String>
     */
    @Override
    public Result<String> getAccount() {
        registry.counter("AccountExternalFacade.getAccount.count").increment();

        String response = moduleService.sayHello();
        logger.info("AccountExternalFacade getAccount, from : {}, message is: {}.", response, message);
        Account account = accountMapper.selectById(2L);
        return Result.ok(JSON.toJSONString(account));
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
