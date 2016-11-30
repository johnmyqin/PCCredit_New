package com.cardpay.controller.organization;

import com.cardpay.util.TestEnv;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by chenkai on 2016/11/30.
 */
public class TOrganizationControllerTest extends TestEnv{
    @Test
    public void queryOrganization() throws Exception {
        mockMvc.perform(get("/organization/all").param("level","3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    public void deleteOrganization() throws Exception {
        mockMvc.perform(delete("/organization/deleteOrganization").param("id","2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

}