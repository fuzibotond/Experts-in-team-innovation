package com.example.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleService {

    public Set<String> determineRoles(String email) {
        Set<String> roles = new HashSet<>();
        if (email.endsWith("@service.com")) {
            roles.add("SERVICE");
        } else {
            roles.add("VIEWER");
        }
        return roles;
    }
}

