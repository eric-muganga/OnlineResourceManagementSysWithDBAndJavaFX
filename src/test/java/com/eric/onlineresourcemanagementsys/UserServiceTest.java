package com.eric.onlineresourcemanagementsys;

import com.eric.onlineresourcemanagementsys.Entity.User;
import com.eric.onlineresourcemanagementsys.repos.UserRepository;
import com.eric.onlineresourcemanagementsys.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        SecretKey secretKey = Mockito.mock(SecretKey.class);
        userService = new UserService(userRepository, secretKey);
    }

    @Test
    void registerUser_Success() {
        // Arrange
        String username = "eric";
        String password = "password123";
        when(userRepository.findByUsername(username)).thenReturn(null);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User user = userService.registerUser(username, password);

        // Assert
        assertNotNull(user);
        assertEquals(username, user.getUsername());
    }

    @Test
    void registerUser_ThrowsException_WhenUserExists() {
        // Arrange
        String username = "eric";
        when(userRepository.findByUsername(username)).thenReturn(new User());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(username, "password123");
        });
        assertEquals("Username already exists. Please log in instead.", exception.getMessage());
    }
}
