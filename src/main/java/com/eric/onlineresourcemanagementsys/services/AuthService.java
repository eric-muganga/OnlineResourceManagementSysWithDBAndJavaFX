package com.eric.onlineresourcemanagementsys.services;


import com.eric.onlineresourcemanagementsys.Entity.User;
import com.eric.onlineresourcemanagementsys.repos.UserRepository;
import com.eric.onlineresourcemanagementsys.utils.EncryptionUtil;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class AuthService {
    private final SecretKey secretKey;
    private final UserRepository userRepository;

    public AuthService(SecretKey key, UserRepository userRepository) {
        this.secretKey = key;
        this.userRepository = userRepository;
    }


    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("Invalid username or password.");
        }

        try {
            assert user != null;
            if (user.verifyPassword(password, secretKey)) {
                return user;
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (Exception e) {
            System.out.println("Error during password verification: " + e.getMessage());
        }
        return null;
    }


}
