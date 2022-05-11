package com.account.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.account.dal.dao.Account;
import com.account.dal.mapper.AccountMapper;
import com.account.facade.AccountInternalFacade;
import com.account.model.Result;
import com.alibaba.fastjson.JSON;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Internal Facade Implement
 *
 * @author king
 * @date 2022/05/07 13:54
 **/
public class AccountInternalFacadeImpl implements AccountInternalFacade {
    private static final Logger logger = LoggerFactory.getLogger(AccountInternalFacadeImpl.class);

    @Autowired
    private AccountMapper accountMapper;

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
        registry.counter("AccountInternalFacade.getAccount.count").increment();

        Account account = accountMapper.selectById(1L);
        return Result.ok(JSON.toJSONString(account));
    }
}
