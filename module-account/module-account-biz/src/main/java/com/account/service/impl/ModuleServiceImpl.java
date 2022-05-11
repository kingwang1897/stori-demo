package com.account.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.account.service.ModuleService;

/**
 * Internal Service Implement
 *
 * @author king
 * @date 2022/05/07 13:59
 **/
@Service("moduleService")
public class ModuleServiceImpl implements ModuleService {
    private static final Logger logger = LoggerFactory.getLogger(ModuleServiceImpl.class);

    /**
     * sayHello
     *
     * @date 2022/05/07 14:00
     * @return java.lang.String
     */
    @Override
    public String sayHello() {
        return "module-account say helllo";
    }
}
