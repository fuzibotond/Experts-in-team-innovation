package com.example;
import com.example.controllers.RealTimeDataController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class RealTimeDataTest
{
    @Autowired
    private RealTimeDataController realTimeDataController;

    @MockBean
    private SimpMessagingTemplate messagingTemplate;

    @Test
    void testListenToKafka() {
        String testMessage = "Test sensor data message";
        realTimeDataController.listenToKafka(testMessage);
        verify(messagingTemplate).convertAndSend("/topic/sensor-data", testMessage);
    }
}
