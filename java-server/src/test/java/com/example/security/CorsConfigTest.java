package com.example.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest // Ensures WebFlux-specific testing is enabled
class CorsConfigTest {

//    @Autowired
//    private WebTestClient webTestClient;
//
//    @Test
//    void shouldRejectRequestFromUnauthorizedOrigin() {
//        webTestClient.get()
//                .uri("/api/data/all")
//                .header("Origin", "http://unauthorized-origin.com")
//                .exchange()
//                .expectStatus().isForbidden(); // Ensure unauthorized origin is rejected
//    }
//
//    @Test
//    void shouldAllowRequestFromAuthorizedOrigin() {
//        webTestClient.get()
//                .uri("/api/data/all")
//                .header("Origin", "http://localhost:4200")
//                .exchange()
//                .expectStatus().isOk(); // Ensure authorized origin is accepted
//    }
}
