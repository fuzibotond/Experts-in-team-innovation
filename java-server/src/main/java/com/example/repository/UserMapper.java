package com.example.repository;

import com.example.repository.data.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Set;

public class UserMapper {

    public static User mapOAuth2UserToUser(OAuth2User oauth2User) {
        User user = new User();
        user.setEmail(oauth2User.getAttribute("email"));
        user.setName(oauth2User.getAttribute("name"));
        user.setPicture(oauth2User.getAttribute("picture") != null ? oauth2User.getAttribute("picture") : "");
        Set<String> roles = new java.util.HashSet<>();
        roles.add("VIEWER");
        user.setRoles(roles); // Default role
        return user;
    }

}
