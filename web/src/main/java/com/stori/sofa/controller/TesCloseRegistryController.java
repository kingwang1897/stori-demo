package com.stori.sofa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.sofa.rpc.context.RpcRuntimeContext;
import com.google.common.base.Throwables;

/**
 * Close Registry Controller
 * 
 * @author king
 * @date 2022/06/07 19:24
 **/
@RestController
public class TesCloseRegistryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TesCloseRegistryController.class);

    /**
     * Close Registry
     *
     * @date 2022/05/05 19:25
     * @return java.lang.String
     */
    @GetMapping("/close/registry")
    public void closeRegistry() {
        try {
            long time1 = System.currentTimeMillis();
            RpcRuntimeContext.destroy();
            LOGGER.info("Close Registry success, time is:{}, costTime is: {}.", System.currentTimeMillis(),
                System.currentTimeMillis() - time1);
        } catch (Exception e) {
            LOGGER.error("Close Registry error, case by:{}.", Throwables.getStackTraceAsString(e));
        }
    }
}
