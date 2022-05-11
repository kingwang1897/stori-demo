package com.stori.sofa.controller;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.stori.sofa.base.BaseTest;

/**
 * 测试用例
 *
 * @author king
 * @date 2022/05/05 19:32
 **/
public class TesModuleBillControllerTest extends BaseTest {

    @Test
    public void testModuleBill() throws Exception {
        MvcResult mvcResult =
            mockMvc.perform(MockMvcRequestBuilders.get("/test/module/bill").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
        String result = mvcResult.getResponse().getContentAsString();
        // Result<String> responseWrapper = new ObjectMapper().readValue(result, Result.class);
        Assert.assertEquals("billExternal is: true", result);
    }

    @Test
    public void testModuleBillReference() throws Exception {
        MvcResult mvcResult =
            mockMvc.perform(MockMvcRequestBuilders.get("/test/module/reference").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
        String result = mvcResult.getResponse().getContentAsString();
        // Result<String> responseWrapper = new ObjectMapper().readValue(result, Result.class);
        Assert.assertEquals("billInternal is: true", result);
    }
}