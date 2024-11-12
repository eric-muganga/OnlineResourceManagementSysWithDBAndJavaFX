package com.eric.onlineresourcemanagementsys;

import com.eric.onlineresourcemanagementsys.menu.MainMenu;
import com.eric.onlineresourcemanagementsys.user.AuthService;
import com.eric.onlineresourcemanagementsys.utils.KeyHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.SecretKey;
import java.io.File;

@SpringBootApplication
public class OnlineResourceManagementSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineResourceManagementSysApplication.class, args);
        try {
            // Loading or generating the SecretKey
            SecretKey secretKey;
            File keyFile = new File("data/secretKey.ser");
            if (keyFile.exists()) {
                secretKey = KeyHandler.loadSecretKey("data/secretKey.ser");
                System.out.println("Secret key loaded from file");
            } else {
                secretKey = KeyHandler.generateSecretKey();
                KeyHandler.saveSecretKey(secretKey, "data/secretKey.ser");
                System.out.println("Secret key saved");
            }

            // Initialize AuthService and Menu
            AuthService authService = new AuthService(secretKey);
            MainMenu mainMenu = new MainMenu(authService);

            // Start the main menu
            mainMenu.display();

        } catch (Exception e) {
            System.out.println("Error starting the application: " + e.getMessage());
        }
    }

}
