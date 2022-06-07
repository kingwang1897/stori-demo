package com.stori.sofa.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.sofa.rpc.context.RpcRuntimeContext;

/**
 * close registry controller
 * 
 * @author king
 * @date 2022/06/07 19:24
 **/
@RestController
public class TesCloseRegistryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TesCloseRegistryController.class);

    /**
     * close test function
     *
     * @date 2022/05/05 19:25
     * @return java.lang.String
     */
    @GetMapping("/test/close")
    public String testClose() throws IOException {
        long time1 = System.currentTimeMillis();
        RpcRuntimeContext.destroy();
        LOGGER.info("testClose success, time is:{}, costTime is: {}.", System.currentTimeMillis(),
            System.currentTimeMillis() - time1);
        return null;
    }
}
