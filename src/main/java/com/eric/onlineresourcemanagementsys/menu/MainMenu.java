package com.eric.onlineresourcemanagementsys.menu;

import com.eric.onlineresourcemanagementsys.services.AuthService;
import com.eric.onlineresourcemanagementsys.services.ResourceService;
import com.eric.onlineresourcemanagementsys.services.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MainMenu {
    private final AuthService authService;
    private final UserService userService;
    private final ResourceService resourceService;
    private final Scanner scanner = new Scanner(System.in);

    public MainMenu(AuthService authService, UserService userService, ResourceService resourceService) {
        this.authService = authService;
        this.userService = userService;
        this.resourceService = resourceService;
    }

    public void display() {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> login();
                    case 2 -> register();
                    case 3 -> {
                        System.out.println("Exiting the application...");
                        System.exit(0);
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid option. Please enter a number between 1 and 3.");
            }
        }
    }

    private void login() {
        System.out.println("\n--- Login ---");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        var user = authService.loginUser(username, password);
        if (user != null) {
            System.out.println("Login successful! Welcome, " + username + ".");
            new UserDashboardMenu(resourceService, userService, user ).display();
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private void register() {
        System.out.println("\n--- Register ---");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        userService.registerUser(username, password);
    }
}
