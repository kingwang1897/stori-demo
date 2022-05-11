package com.stori.sofa.base;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.account.facade.AccountInternalFacade;
import com.bill.facade.BillExternalFacade;
import com.bill.facade.BillInternalFacade;
import com.bill.model.Result;

/**
 * @author king
 * @date 2022/05/05 20:37
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class BaseTest {

    public MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    @Autowired
    public AccountInternalFacade accountInternalFacade;

    @MockBean
    @Autowired
    public BillInternalFacade billInternalFacade;

    @MockBean
    @Autowired
    public BillExternalFacade billExternalFacade;

    /**
     * 前置步骤
     */
    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // 测试桩
        Mockito.when(billInternalFacade.getBill()).thenReturn(Result.ok("true"));
        Mockito.when(billExternalFacade.getBill()).thenReturn(Result.ok("true"));

        Mockito.when(accountInternalFacade.getAccount()).thenReturn(com.account.model.Result.ok("true"));
    }
}
