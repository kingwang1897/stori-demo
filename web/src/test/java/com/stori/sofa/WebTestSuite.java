package com.stori.sofa;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.stori.sofa.controller.TesModuleBillControllerTest;
import com.stori.sofa.controller.TestModuleAccountControllerTest;
import com.stori.sofa.mapper.BillMapperTest;

/**
 * @author king
 * @date 2022/05/11 10:43
 **/
@RunWith(Suite.class)
@SuiteClasses({TesModuleBillControllerTest.class, TestModuleAccountControllerTest.class, BillMapperTest.class})
public class WebTestSuite {}
