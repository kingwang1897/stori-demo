package com.stori.sofa.controller;

import com.bill.common.facade.BillFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TesModuleController {

    // init the logger
    private static final Logger LOGGER = LoggerFactory.getLogger(TestMySqlController.class);

    @Autowired
    private BillFacade billFacade;

    @GetMapping("/test/module")
    public String testModule() throws IOException {

        return billFacade.getBill();
    }
}
