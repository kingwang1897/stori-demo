package com.stori.sofa.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bill.common.facade.BillFacade;
import com.stori.sofa.model.Result;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * module test controller
 * 
 * @author wangkai
 * @date 2022/05/05 19:24
 **/
@RestController
public class TesModuleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestMySqlController.class);

    @Autowired
    private BillFacade billFacade;

    @Autowired
    private MeterRegistry registry;

    /**
     * module test function
     * 
     * @date 2022/05/05 19:25
     * @return java.lang.String
     */
    @GetMapping("/test/module")
    public String testModule() throws IOException {
        registry.counter("TesModuleController.testModule.count").increment();

        Result<String> bill = billFacade.getBill();
        return bill.isSuccess() ? bill.getData() : bill.getMsg();
    }
}
