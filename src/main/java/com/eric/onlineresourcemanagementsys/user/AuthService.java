package com.eric.onlineresourcemanagementsys.user;


import com.eric.onlineresourcemanagementsys.utils.FileHandler;

import javax.crypto.SecretKey;


public class AuthService {
    private SecretKey secretKey;

    public AuthService(SecretKey key) {
        this.secretKey = key;
    }

    public void registerUser(String username, String password) {
        // Check if the user already exists in the file
        if (FileHandler.loadUserFromFile(username) != null) {
            throw new IllegalArgumentException("User already exists");
        }
        // Creating a new user and save directly to file
        User newUser = new User(username, password, secretKey);
        FileHandler.saveUserToFile(newUser);// Saving user to persistent storage
        FileHandler.saveUserResourcesToFile(newUser); // Saving empty resources
        System.out.println("Registration successful");
    }

    public User loginUser(String username, String password) {
        // Loading user data from file each time
        User user = FileHandler.loadUserFromFile(username);
        if (user != null && user.verifyPassword(password, secretKey)) {
            // Loading the user's resources from file after successful login
            var resources = FileHandler.loadUserResourcesFromFile(user);
            if (resources != null) {
                user.getResourceManager().setResources(resources);
            } else {
                System.out.println("No resources found for user: " + username);
            }
            return user;
        } else {
            System.out.println("Invalid username or password.");
            return null;
        }
    }
}
