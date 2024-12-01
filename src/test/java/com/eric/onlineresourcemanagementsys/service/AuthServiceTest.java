package com.eric.onlineresourcemanagementsys.service;

import com.eric.onlineresourcemanagementsys.user.AuthService;
import com.eric.onlineresourcemanagementsys.Entity.User;
import com.eric.onlineresourcemanagementsys.utils.FileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {
    private AuthService authService;
    private SecretKey secretKey;

    @BeforeEach
    void setUp() throws Exception {
        // Clear user and resource files for a clean state
        FileHandler.clearTestFiles();

        // Generate the secret key once
        if (secretKey == null) {
            secretKey = KeyGenerator.getInstance("AES").generateKey();
        }
        authService = new AuthService(secretKey);
    }


    @Test
    void testRegisterUser() {
        // Register a new user
        authService.registerUser("ericTest", "password123");

        // Verify that the user was registered successfully
        User user = authService.loginUser("ericTest", "password123");

        assertNotNull(user, "User should not be null after registration and login");
        assertEquals("ericTest", user.getUsername(), "Usernames should match");
    }

    @Test
    void testLoginWithInvalidCredentials() {
        authService.registerUser("ericTest", "password123");

        // Attempt to login with invalid credentials
        User user = authService.loginUser("ericTest", "wrongpassword");

        // Verify that login fails and returns null
        assertNull(user, "User should not be able to login with invalid credentials");
    }

    @Test
    void testRegisterDuplicateUserThrowsException() {
        authService.registerUser("ericTest", "password123");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.registerUser("ericTest", "password123");
        });

        assertEquals("User already exists", exception.getMessage(), "Exception message should indicate duplicate user");
    }
}
