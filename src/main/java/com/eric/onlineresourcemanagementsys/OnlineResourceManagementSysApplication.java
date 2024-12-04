package com.eric.onlineresourcemanagementsys;

import com.eric.onlineresourcemanagementsys.menu.MainMenu;
import com.eric.onlineresourcemanagementsys.repos.UserRepository;
import com.eric.onlineresourcemanagementsys.services.AuthService;
import com.eric.onlineresourcemanagementsys.services.UserService;
import com.eric.onlineresourcemanagementsys.utils.KeyHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.crypto.SecretKey;
import java.io.File;

@SpringBootApplication
public class OnlineResourceManagementSysApplication {
    public static void main(String[] args) {

        SpringApplication.run(OnlineResourceManagementSysApplication.class, args);

    }

    @Bean
    public CommandLineRunner run(MainMenu mainMenu) {
        return args -> {
            try {
                mainMenu.display();
            } catch (Exception e) {
                System.err.println("An error occurred while running the application: " + e.getMessage());
            }
        };
    }

}
