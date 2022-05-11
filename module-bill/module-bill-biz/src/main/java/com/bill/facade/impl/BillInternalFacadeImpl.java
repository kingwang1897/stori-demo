package com.bill.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.account.facade.AccountExternalFacade;
import com.alibaba.fastjson.JSON;
import com.bill.dal.dao.Bill;
import com.bill.dal.mapper.BillMapper;
import com.bill.facade.BillInternalFacade;
import com.bill.model.Result;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Internal Facade Implement
 * 
 * @author king
 * @date 2022/05/07 13:54
 **/
public class BillInternalFacadeImpl implements BillInternalFacade {
    private static final Logger logger = LoggerFactory.getLogger(BillInternalFacadeImpl.class);

    @Autowired
    private MeterRegistry registry;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private AccountExternalFacade accountExternalFacade;

    /**
     * getBill
     *
     * @date 2022/05/07 14:01
     * @return com.stori.sofa.model.Result<java.lang.String>
     */
    @Override
    public Result<String> getBill() {
        registry.counter("BillInternalFacade.getBill.count").increment();

        com.account.model.Result<String> accountExternalResult = accountExternalFacade.getAccount();
        Bill bill = billMapper.selectBillById(1L);
        logger.info("BillInternalFacade getBill, account is : {}, bill is: {}.",
            JSON.toJSONString(accountExternalResult), JSON.toJSONString(bill));
        return Result.ok(JSON.toJSONString(bill) + ", reference is : " + JSON.toJSONString(accountExternalResult));
    }
}
