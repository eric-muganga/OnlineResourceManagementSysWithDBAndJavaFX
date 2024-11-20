package com.eric.onlineresourcemanagementsys.service;

import com.eric.onlineresourcemanagementsys.user.AuthService;
import com.eric.onlineresourcemanagementsys.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AuthServiceTest {
    private AuthService authService;
    private SecretKey secretKey;

    @BeforeEach
    void setUp() throws Exception {
        // Generate a secret key for testing
        secretKey = KeyGenerator.getInstance("AES").generateKey();
        authService = new AuthService(secretKey);
    }

    @Test
    void testRegisterUser() {
        // Register a new user
        authService.registerUser("testUser", "testPassword");

        // Verify that the user was registered successfully
        User user = authService.loginUser("testUser", "testPassword");
        assertNotNull(user, "User should be registered and logged in successfully");
    }

    @Test
    void testLoginWithInvalidCredentials() {
        // Attempt to login with invalid credentials
        User user = authService.loginUser("invalidUser", "invalidPassword");

        // Verify that login fails and returns null
        assertNull(user, "User should not be able to login with invalid credentials");
    }
}
