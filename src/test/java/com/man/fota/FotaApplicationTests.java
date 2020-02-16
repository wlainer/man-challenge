package com.man.fota;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
class FotaApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getAllFeatures() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/fota/features")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(6)));
    }

    @Test
    public void getAllFeaturesForVehicle_Test_A() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/fota/features/Test_A")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.compatibleVehicles").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.compatibleVehicles", hasSize(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.incompatibleVehicles").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.incompatibleVehicles", hasSize(0)));
    }

    @Test
    public void getCompatibleFeaturesForTest_A() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/fota/features/Test_A/installable")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }

    @Test
    public void getIncompatibleFeaturesForTest_A() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/fota/features/Test_A/incompatible")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
    }

    @Test
    public void getAllVehicles() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/fota/vehicles")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void getFeaturesForVehicle_VIN_1() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/fota/vehicles/VIN_1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.compatible").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.compatible", hasSize(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.incompatible").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.incompatible", hasSize(5)));
    }

    @Test
    public void getCompatibleFeaturesForVehicle_VIN_1() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/fota/vehicles/VIN_1/installable")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }

    @Test
    public void getIncompatibleFeaturesForVehicle_VIN_1() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/fota/vehicles/VIN_1/incompatible")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(5)));
    }

}
