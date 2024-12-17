package com.example;
import com.example.controllers.DataController;
import com.example.repository.SensorDataRepository;
import com.example.repository.data.SensorData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DataController.class)
public class SensorTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SensorDataRepository sensorDataRepository;

    @Test
    public void testSensorData() {
        SensorData sensorData = new SensorData();
        String expectedId = "123";
        String expectedSensorType = "Temperature";
        Double expectedData = 25.5;
        LocalDateTime expectedTimestamp = LocalDateTime.now();

        sensorData.setId(expectedId);
        sensorData.setSensorType(expectedSensorType);
        sensorData.setData(expectedData);
        sensorData.setTimestamp(expectedTimestamp);

        assertEquals(expectedId, sensorData.getId());
        assertEquals(expectedSensorType, sensorData.getSensorType());
        assertEquals(expectedData, sensorData.getData());
        assertEquals(expectedTimestamp, sensorData.getTimestamp());
    }

    @Test
    public void testGetAllSensorData() throws Exception {
        SensorData sensor1 = new SensorData();
        sensor1.setId("1");
        sensor1.setSensorType("Temperature");
        sensor1.setData(22.5);
        sensor1.setTimestamp(LocalDateTime.now());

        SensorData sensor2 = new SensorData();
        sensor2.setId("2");
        sensor2.setSensorType("Humidity");
        sensor2.setData(55.0);
        sensor2.setTimestamp(LocalDateTime.now());

        List<SensorData> mockData = Arrays.asList(sensor1, sensor2);

        when(sensorDataRepository.findAll()).thenReturn(mockData);

        mockMvc.perform(get("/api/data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].sensorType", is("Temperature")))
                .andExpect(jsonPath("$[1].sensorType", is("Humidity")));
    }

    @Test
    public void testGetSensorDataByType() throws Exception {
        SensorData sensor = new SensorData();
        sensor.setId("1");
        sensor.setSensorType("Temperature");
        sensor.setData(22.5);
        sensor.setTimestamp(LocalDateTime.now());

        when(sensorDataRepository.findAllBySensorType("Temperature"))
                .thenReturn(List.of(sensor));

        mockMvc.perform(get("/api/data/Temperature"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].sensorType", is("Temperature")))
                .andExpect(jsonPath("$[0].data", is(22.5)));
    }
}
