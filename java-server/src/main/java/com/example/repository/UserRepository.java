package com.example.repository;

import com.example.repository.data.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    // Add custom queries if needed
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
