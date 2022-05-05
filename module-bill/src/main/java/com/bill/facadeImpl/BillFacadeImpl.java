package com.bill.facadeImpl;

import com.alibaba.fastjson.JSON;
import com.bill.common.dal.dao.Bill;
import com.bill.common.facade.BillFacade;
import com.bill.common.dal.mapper.BillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * external interface implement
 */
@Service("billFacade")
public class BillFacadeImpl implements BillFacade {
    private static final Logger logger = LoggerFactory.getLogger(BillFacadeImpl.class);

    @Autowired
    private BillMapper billMapper;

    @Override
    public String getBill() {
        logger.info("BillFacade getBill, from : module-bill.");
        Bill bill = billMapper.selectBillById(1L);

        return "BillFacade getBill, from : module-bill, and bill is: " + JSON.toJSONString(bill);
    }
}
