package com.example.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
public class AuthControllerTest {

    @Value("${spring.security.oauth2.resourceserver.opaque-token.clientId}")
    private String clientId;

    @Value("${spring.security.oauth2.resourceserver.opaque-token.clientSecret}")
    private String clientSecret;

    private MockMvc mockMvc;

    @InjectMocks
    private AuthController authController;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testAuthUrl() throws Exception {
        String expectedUrl = "https://accounts.google.com/o/oauth2/auth?client_id=" + clientId
                + "&redirect_uri=http://localhost:4200&response_type=code&scope=email%20profile%20openid";

        mockMvc.perform(get("/auth/url")
                        .param("clientId", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value(expectedUrl));
    }


    @Test
    public void testCallback_Failed() throws Exception {
        mockMvc.perform(get("/auth/callback")
                        .param("code", "test")
                        .param("clientId", clientId)
                        .param("clientSecret", clientSecret))
                        .andExpect(status().isUnauthorized());
    }
}
