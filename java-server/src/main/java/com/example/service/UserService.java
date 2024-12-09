package com.example.service;

import com.example.repository.UserRepository;
import com.example.repository.data.User;
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
        if (!userRepository.findByEmail(email).isPresent()) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(oAuth2User.getAttribute("name"));
            userRepository.save(newUser);
        }
    }

    @Transactional
    public boolean approveUser(final String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent()){
            User user = byEmail.get();
            user.setApproved(true);
            userRepository.save(user);
            return true;
        }

        return false;
    }
}
