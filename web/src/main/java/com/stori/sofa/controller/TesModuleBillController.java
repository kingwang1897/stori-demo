package com.stori.sofa.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bill.facade.BillExternalFacade;
import com.bill.model.Result;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * module test controller
 * 
 * @author king
 * @date 2022/05/05 19:24
 **/
@RestController
public class TesModuleBillController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TesModuleBillController.class);

    @Autowired
    private BillExternalFacade billExternalFacade;

    @Autowired
    private MeterRegistry registry;

    // 因为没有引入依赖，所以打开注释会报错
    // @Autowired
    // private BillInternalFacade billInternalFacade;

    /**
     * module-bill test function
     * 
     * @date 2022/05/05 19:25
     * @return java.lang.String
     */
    @GetMapping("/test/module/bill")
    public String testModuleBill() throws IOException {
        registry.counter("TesModuleBillController.ModuleBill.count").increment();

        Result<String> billExternal = billExternalFacade.getBill();
        return "billExternal is: " + billExternal.getData();
    }
}
