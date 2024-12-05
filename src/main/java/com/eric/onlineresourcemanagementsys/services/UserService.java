package com.eric.onlineresourcemanagementsys.services;

import com.eric.onlineresourcemanagementsys.Entity.User;
import com.eric.onlineresourcemanagementsys.repos.UserRepository;
import com.eric.onlineresourcemanagementsys.utils.EncryptionUtil;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SecretKey secretKey;

    public UserService(UserRepository userRepository, SecretKey secretKey) {
        this.userRepository = userRepository;
        this.secretKey = secretKey;
    }

    // Registering  a new user
    public User registerUser(String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists. Please log in instead.");
        }
        byte[] encryptedPassword = EncryptionUtil.encryptPassword(password, secretKey);
        User user = new User(username, encryptedPassword);
        System.out.println("Registration successful");
        return userRepository.save(user);
    }

    // Deleting a user
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }
}
