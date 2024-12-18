package com.example.service;

import com.example.repository.UserRepository;
import com.example.repository.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void processOAuthPostLogin(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        if (userRepository.findByEmail(email).isEmpty()) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(oAuth2User.getAttribute("name"));
            userRepository.save(newUser);
        }
    }

    @Transactional
    public boolean approveUser(final String email) {
            return userRepository.findByEmail(email).map(user -> {
                user.setApproved(true);
                userRepository.save(user);
                return true;
            }).orElse(false);
    }

}
