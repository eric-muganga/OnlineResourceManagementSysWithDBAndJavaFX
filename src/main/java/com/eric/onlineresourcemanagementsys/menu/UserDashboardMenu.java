package com.eric.onlineresourcemanagementsys.menu;

import com.eric.onlineresourcemanagementsys.resource_management.GameAccount;
import com.eric.onlineresourcemanagementsys.resource_management.Resource;
import com.eric.onlineresourcemanagementsys.resource_management.ResourceManager;
import com.eric.onlineresourcemanagementsys.resource_management.Subscription;
import com.eric.onlineresourcemanagementsys.user.User;
import com.eric.onlineresourcemanagementsys.utils.FileHandler;

import java.util.Scanner;

public class UserDashboardMenu {
    private final User user;
    private final ResourceManager resourceManager;
    private final Scanner scanner = new Scanner(System.in);

    public UserDashboardMenu(User user) {
        this.user = user;
        this.resourceManager = user.getResourceManager();
    }

    public void display() {
        while (true) {
            System.out.println("\n--- User Dashboard ---");
            System.out.println("1. View Resources");
            System.out.println("2. Add Resource");
            System.out.println("3. Edit Resource");
            System.out.println("4. Remove Resource");
            System.out.println("5. Logout");
            System.out.print("Select an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> viewResources();
                    case 2 -> addResource();
                    case 3 -> editResource();
                    case 4 -> removeResource();
                    case 5 -> {
                        System.out.println("Logging out...");
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid option. Please enter a number.");
            }
        }
    }

    // Methods for each option: viewResources, addResource, editResource, removeResource
    private void viewResources() {
        System.out.println("Viewing resources for " + user.getUsername());
        resourceManager.getResources().forEach(Resource::displayInfo);
    }

    private void addResource() {
        // Logic for adding resources
        System.out.println("Adding a resource for " + user.getUsername());
        System.out.println("\n--- Add Resource Menu ---");
        System.out.println("1. Add a Game account");
        System.out.println("2. Add a Subscription");
        System.out.println("\n");
        System.out.println("Select an option: ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Adding a game account");
                    addGameAccount();  // Pass User object
                    break;
                case 2:
                    System.out.println("Adding a subscription");
                    addSubscription();  // Pass User object
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid option. Please enter a number.");
        }
    }

    private void editResource() {
        // Logic for editing resources
        System.out.println("\n--- Edit Resource ---");

        resourceManager.getResources().forEach(Resource::displayInfo);

        System.out.println("Enter the name of the resource to edit: ");
        String resourceName = scanner.nextLine();
        Resource resourceToEdit = resourceManager.getResource(resourceName);
        if (resourceToEdit != null) {
            if (resourceToEdit instanceof GameAccount) {
                System.out.println("Edit a GameAccount: " );

                System.out.println("Enter new Game Name (current: " + resourceToEdit.getName() + "): ");
                String newGameName = scanner.nextLine();
                if (!newGameName.isEmpty() && !newGameName.equals(resourceToEdit.getName())) {
                    resourceToEdit.setName(newGameName);
                }

                System.out.println("Enter new Username (current: " + resourceToEdit.getUsername()+ "): ");
                String newUsername = scanner.nextLine();
                if (!newUsername.isEmpty() && !newUsername.equals(resourceToEdit.getUsername())) {
                    resourceToEdit.setUsername(newUsername);
                }

                System.out.println("Enter new Password (current: " + resourceToEdit.getPassword() + "): ");
                String newPassword = scanner.nextLine();
                if (!newPassword.isEmpty() && !newPassword.equals(resourceToEdit.getPassword())) {
                    resourceToEdit.setPassword(newPassword);
                }

                System.out.println("Game account updated successfully.");


            } else if (resourceToEdit instanceof Subscription) {
                System.out.println("Editing a Subscription");


                System.out.println("Enter new Subscription Name (current: " + resourceToEdit.getName() + "): ");
                String newSubscriptionName = scanner.nextLine();
                if (!newSubscriptionName.isEmpty() && !newSubscriptionName.equals(resourceToEdit.getName())) {
                    resourceToEdit.setName(newSubscriptionName);
                }

                System.out.println("Enter new Username (current: " + resourceToEdit.getUsername() + "): ");
                String newUsername = scanner.nextLine();
                if (!newUsername.isEmpty() && !newUsername.equals(resourceToEdit.getUsername())) {
                    resourceToEdit.setUsername(newUsername);
                }

                System.out.println("Enter new Password (current: " + resourceToEdit.getPassword() + "): ");
                String newPassword = scanner.nextLine();
                if (!newPassword.isEmpty() && !newPassword.equals(resourceToEdit.getPassword())) {
                    resourceToEdit.setPassword(newPassword);
                }


                System.out.println("Subscription updated successfully.");
            }

            FileHandler.saveUserResourcesToFile(user);

        }else {
            System.out.println("No resource found with the name: " + resourceName);
        }
    }

    private void removeResource() {
        // Logic for removing resources

        System.out.println("\n--- Remove Resource ---");

        // Prompt user for the name of the resource to remove
        System.out.println("Enter the name of the resource to remove: ");
        String resourceName = scanner.nextLine();

        // Find the resource to remove
        Resource resourceToRemove = resourceManager.getResource(resourceName);

        if (resourceToRemove != null) {
            resourceManager.removeResource(resourceToRemove); // Remove the resource using ResourceManager
            System.out.println("Resource removed successfully.");
        } else {
            System.out.println("No resource found with the name: " + resourceName);
        }
    }

    private void addGameAccount() {
        System.out.println("\n--- Add Game Account ---");

        // Prompt user for game account details
        System.out.println("Enter Game Name: ");
        String gameName = scanner.nextLine();

        System.out.println("Enter Username: ");
        String username = scanner.nextLine();

        System.out.println("Enter Password: ");
        String password = scanner.nextLine();

        System.out.println("Enter Platform (e.g., PC, Xbox, PlayStation): ");
        String platform = scanner.nextLine();

        // Creating a new GameAccount object
        GameAccount gameAccount = new GameAccount(gameName, username, password, platform);

        // Add the resource to the user's resource list
        resourceManager.addResource(gameAccount);

        // Save the user's resources to persistent storage
        FileHandler.saveUserResourcesToFile(user);
        System.out.println("Game account added successfully!");
    }


    private void addSubscription() {
        System.out.println("\n--- Add Subscription ---");

        // Prompt user for subscription details
        System.out.println("Enter Subscription Name (e.g., Netflix, Spotify): ");
        String subscriptionName = scanner.nextLine();

        System.out.println("Enter Username: ");
        String username = scanner.nextLine();

        System.out.println("Enter Password: ");
        String password = scanner.nextLine();

        System.out.println("Enter Subscription Type (e.g., Streaming, Magazine): ");
        String subscriptionType = scanner.nextLine();

        // Creating a new Subscription object
        Subscription subscription = new Subscription(subscriptionName, username, password, subscriptionType);

        // Add the resource to the user's resource list
        resourceManager.addResource(subscription);

        // Save the user's resources to persistent storage
        FileHandler.saveUserResourcesToFile(user);
        System.out.println("Subscription added successfully!");
    }
}
