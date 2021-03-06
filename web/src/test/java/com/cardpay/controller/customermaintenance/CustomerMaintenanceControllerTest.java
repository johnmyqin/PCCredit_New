package com.cardpay.controller.customermaintenance;

import com.cardpay.util.TestEnv;
import org.junit.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * 客户维护controller单元测试
 *@author wangpeng
 */
public class CustomerMaintenanceControllerTest extends TestEnv {
    @Test
    public void getMaintenanceTypeList() throws Exception {
        mockMvc.perform(get("/customermaintenance/maintenanceTypelist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    public void getMaintenanceList() throws Exception {
        mockMvc.perform(get("/customermaintenance/maintenancelist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

}