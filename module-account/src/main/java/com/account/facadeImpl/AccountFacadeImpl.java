package com.account.facadeImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.account.common.dal.dao.Account;
import com.account.common.dal.mapper.AccountMapper;
import com.account.common.facade.AccountFacade;
import com.account.common.service.ModuleService;
import com.alibaba.fastjson.JSON;
import com.stori.sofa.model.Result;

/**
 * external interface implement
 */
@Service("accountFacade")
public class AccountFacadeImpl implements AccountFacade {
    private static final Logger logger = LoggerFactory.getLogger(AccountFacadeImpl.class);

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private ModuleService moduleService;

    @Override
    public Result<String> getAccount() {
        String response = moduleService.sayHello();
        logger.info("AccountFacadeImpl getAccount, from : {}.", response);

        Account account = accountMapper.selectById(1L);
        return Result.ok(JSON.toJSONString(account));
    }
}
