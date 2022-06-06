package com.stori.sofa.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stori.sofa.message.producer.ProducerManager;

/**
 * module test controller
 * 
 * @author king
 * @date 2022/05/05 19:24
 **/
@RestController
public class TesMqController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TesMqController.class);

    @Autowired
    private ProducerManager producerManager;

    /**
     * module-bill test function
     * 
     * @date 2022/05/05 19:25
     * @return java.lang.String
     */
    @GetMapping("/test/Mq")
    public String testMq() throws IOException {

        return "billExternal is: " + producerManager.sendMessage("Ok");
    }
}
