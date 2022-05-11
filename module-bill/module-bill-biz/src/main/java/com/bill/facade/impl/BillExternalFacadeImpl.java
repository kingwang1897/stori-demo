package com.bill.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.bill.dal.dao.Bill;
import com.bill.dal.mapper.BillMapper;
import com.bill.facade.BillExternalFacade;
import com.bill.model.Result;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * External Facade Implement
 *
 * @author king
 * @date 2022/05/07 13:54
 **/
public class BillExternalFacadeImpl implements BillExternalFacade {
    private static final Logger logger = LoggerFactory.getLogger(BillExternalFacadeImpl.class);

    @Autowired
    private MeterRegistry registry;

    @Autowired
    private BillMapper billMapper;

    /**
     * getBill
     *
     * @date 2022/05/07 14:01
     * @return com.stori.sofa.model.Result<java.lang.String>
     */
    @Override
    public Result<String> getBill() {
        registry.counter("billExternalFacade.getBill.count").increment();

        logger.info("billExternalFacade getBill, from : module-bill.");
        Bill bill = billMapper.selectBillById(2L);
        return Result.ok(JSON.toJSONString(bill));
    }
}
