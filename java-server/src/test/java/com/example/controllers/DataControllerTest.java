package com.example.controllers;

import com.example.repository.SensorDataRepository;
import com.example.repository.data.SensorData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class DataControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SensorDataRepository sensorDataRepository;

    @InjectMocks
    private DataController dataController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(dataController).build();
    }

    @Test
    void testGetAllSensorData() throws Exception {
        SensorData mockData = new SensorData();
        mockData.setId("123");
        mockData.setTopic("temperatureTopic");
        mockData.setSensorType("Temperature");
        mockData.setData("25.5");
        mockData.setTimestamp(LocalDateTime.now());
        List<SensorData> mockDataList = Collections.singletonList(mockData);

        when(sensorDataRepository.findAll()).thenReturn(mockDataList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/data/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].sensorType").value("Temperature"));
    }

    @Test
    void testGetSensorDataByType() throws Exception {
        SensorData mockData = new SensorData();
        mockData.setId("123");
        mockData.setTopic("temperatureTopic");
        mockData.setSensorType("Temperature");
        mockData.setData("25.5");
        mockData.setTimestamp(LocalDateTime.now());
        List<SensorData> mockDataList = Collections.singletonList(mockData);

        when(sensorDataRepository.findAllBySensorType("Temperature")).thenReturn(mockDataList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/data/Temperature"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].sensorType").value("Temperature"));
    }

    @Test
    void testDeleteSensorData() throws Exception {
        String sensorId = "123";
        doNothing().when(sensorDataRepository).deleteById(sensorId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/data/remove")
                        .param("id", sensorId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Data has been deleted successfully with id: 123"));
    }

    @Test
    void testDeleteSensorDataFails() throws Exception {
        String sensorId = "123";
        doThrow(new RuntimeException()).when(sensorDataRepository).deleteById(sensorId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/data/remove")
                        .param("id", sensorId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Unable to delete data with id: 123"));
    }

    @Test
    void testDeleteAllSensorData() throws Exception {
        doNothing().when(sensorDataRepository).deleteAll();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/data/remove/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("All data has been deleted successfully!"));
    }

    @Test
    void testDeleteAllSensorDataFails() throws Exception {
        doThrow(new RuntimeException()).when(sensorDataRepository).deleteAll();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/data/remove/all"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Unable to delete all data:"));
    }
}
