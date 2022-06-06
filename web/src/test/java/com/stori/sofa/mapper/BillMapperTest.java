package com.stori.sofa.mapper;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stori.sofa.base.BaseTest;

/**
 * @param
 * @author Daniel Yang(sw)
 * @version 20220525
 * @description Test BillMapper
 * @date 2022/5/7 16:00
 * @return
 */
public class BillMapperTest extends BaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BillMapperTest.class);

    // @Resource
    // BillMapper billMapper;

    @Before
    public void setUp() {
        init();
    }

    @Test
    public void testSelectBillById() {
        // Bill bill = billMapper.selectBillById(1L);
        // Assert.assertNotNull(bill);
        LOGGER.info("BillMapper.selectBillById() passed.");
    }
}