package com.example.controllers;


import com.example.dto.UserInfo;
import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/user-info")
    public ResponseEntity<UserInfo> privateMessages() {
        OAuth2AccessToken credentials = (OAuth2AccessToken) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        UserInfo userInfo = getProfileDetailsGoogle(credentials.getTokenValue());

        if (credentials == null) {
            throw new RuntimeException("User is not authenticated");
        }

        return ResponseEntity.ok(userInfo);
    }

    private UserInfo getProfileDetailsGoogle(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);

        String url = "https://www.googleapis.com/oauth2/v2/userinfo";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        return new Gson().fromJson(response.getBody(), UserInfo.class);
    }
}
