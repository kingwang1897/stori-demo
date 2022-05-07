package com.bill.facadeImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service("billInternalFacade")
public class BillInternalFacadeImpl implements BillInternalFacade {
    private static final Logger logger = LoggerFactory.getLogger(BillInternalFacadeImpl.class);

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
        registry.counter("BillInternalFacade.getBill.count").increment();

        logger.info("BillInternalFacade getBill, from : module-bill.");
        Bill bill = billMapper.selectBillById(1L);
        return Result.ok(JSON.toJSONString(bill));
    }
}
