package com.stori.sofa.schedule;

import com.account.common.facade.AccountFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DemoSchedule {

    @Autowired
    private AccountFacade accountFacade;

//    @Scheduled(fixedDelay = 1000L)
//    public void demo() {
//        System.out.println("Start: " + accountFacade.getAccount());
//    }
}
