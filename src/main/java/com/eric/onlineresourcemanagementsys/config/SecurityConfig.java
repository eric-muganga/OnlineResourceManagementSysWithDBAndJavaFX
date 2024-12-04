package com.eric.onlineresourcemanagementsys.config;

import com.eric.onlineresourcemanagementsys.utils.KeyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;

@Configuration
public class SecurityConfig {
    @Bean
    public SecretKey secretKey() throws Exception {
        try {
            // Defining the key file path
            String keyFilePath = "data/secretKey.ser";
            File keyFile = new File("data/secretKey.ser");

            // Loading the key if it exists
            if (keyFile.exists()) {
                return KeyHandler.loadSecretKey("data/secretKey.ser");
            } else {
                // Generate and save a new key
                SecretKey secretKey = KeyHandler.generateSecretKey();
                KeyHandler.saveSecretKey(secretKey, "data/secretKey.ser");
                return secretKey;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error managing SecretKey: " + e.getMessage(), e);
        }
    }
}
