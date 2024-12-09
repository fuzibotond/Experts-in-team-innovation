package com.example.config;

import com.example.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class GoogleOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final WebClient userInfoClient;

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        UserInfo userInfo = userInfoClient.get()
                .uri( uriBuilder -> uriBuilder
                        .path("/oauth2/v3/userinfo")
                        .queryParam("access_token", token)
                        .build())
                .retrieve()
                .bodyToMono(UserInfo.class)
                .block();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", userInfo.getSub());
        attributes.put("name", userInfo.getName());
        attributes.put("givenName", userInfo.getGiven_name());
        attributes.put("familyName", userInfo.getFamily_name());
        attributes.put("picture", userInfo.getPicture());
        attributes.put("email", userInfo.getEmail());
        attributes.put("locale", userInfo.getLocale());

        return new OAuth2IntrospectionAuthenticatedPrincipal(userInfo.getName(), attributes, null);
    }
}
