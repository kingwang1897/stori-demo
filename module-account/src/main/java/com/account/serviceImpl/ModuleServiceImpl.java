package com.account.serviceImpl;

import com.account.common.service.ModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * internal service implement
 */
@Service("moduleService")
public class ModuleServiceImpl implements ModuleService {
    private static final Logger logger = LoggerFactory.getLogger(ModuleServiceImpl.class);

    @Override
    public String sayHello() {
        return "module-account say helllo";
    }
}
