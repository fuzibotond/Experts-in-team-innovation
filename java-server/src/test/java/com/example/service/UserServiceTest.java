package com.example.service;
import com.example.repository.UserRepository;
import com.example.repository.data.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Test
    void testApproveEmail() {
        String email = "existinguser@example.com";
        Set<String> roles = new HashSet<>();
        User user = new User("user", email, "User Name", "profile-pic-url", false, roles);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        boolean result = userService.approveUser(email);

        assertTrue(result, "The approveUser method should return true for an existing user.");
        assertTrue(user.getApproved(), "The user's approved status should be updated to true.");
        verify(userRepository, times(1)).save(user);
    }
}
