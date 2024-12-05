package com.eric.onlineresourcemanagementsys.menu;

import com.eric.onlineresourcemanagementsys.Entity.GameAccount;
import com.eric.onlineresourcemanagementsys.Entity.Resource;
import com.eric.onlineresourcemanagementsys.Entity.Subscription;
import com.eric.onlineresourcemanagementsys.Entity.User;
import com.eric.onlineresourcemanagementsys.services.ResourceService;
import com.eric.onlineresourcemanagementsys.services.UserService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserDashboardMenu {
    // Declaring dependencies for ResourceService, UserService, and the current User
    private final ResourceService resourceService;
    private final UserService userService;
    private final User user;
    private final Scanner scanner = new Scanner(System.in);

    // Injecting services and user object via constructor
    public UserDashboardMenu(ResourceService resourceService, UserService userService, User user) {
        this.resourceService = resourceService;
        this.userService = userService;
        this.user = user;
    }

    // Displaying the main dashboard menu to the user
    public void display() {
        while (true) {
            // Showing dashboard options to the user
            System.out.println("\n--- User Dashboard ---");
            System.out.println("1. View Resources");
            System.out.println("2. Add Resource");
            System.out.println("3. Edit Resource");
            System.out.println("4. Remove Resource");
            System.out.println("5. Logout");
            System.out.print("Select an option: ");

            try {
                // Reading the user's choice
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> viewResources(); // Handling the 'View Resources' option
                    case 2 -> addResource();   // Handling the 'Add Resource' option
                    case 3 -> editResource();  // Handling the 'Edit Resource' option
                    case 4 -> removeResource();// Handling the 'Remove Resource' option
                    case 5 -> {
                        System.out.println("Logging out...");
                        return; // Exiting the menu loop to log out
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid option. Please enter a number.");
            }
        }
    }

    // Fetching and displaying all resources associated with the user
    private void viewResources() {
        Map<String, List<Resource>> groupedResources = resourceService.groupResourcesByType(user.getId()); // Fetching resources from the service
        if (groupedResources.isEmpty()) {
            // Informing the user if no resources are found
            System.out.println("No resources found for user: " + user.getUsername());
        } else {
            // Displaying all resources
            System.out.println("\n\n Resources for " + user.getUsername() + ":");
            groupedResources.forEach((type, resources) -> {
                System.out.println("\n--- " + type + " ---");
                resources.forEach(Resource::displayInfo);
            }); // Iterating through resources and displaying their details
        }
    }

    // Displaying the 'Add Resource' menu and handling user input
    private void addResource() {
        System.out.println("\n\nAdding a resource for " + user.getUsername());
        System.out.println("\n--- Add Resource Menu ---");
        System.out.println("1. Add a Game Account");
        System.out.println("2. Add a Subscription");
        System.out.println("\n");
        System.out.print("Select an option: ");

        try {
            // Reading the user's choice for resource type
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> addGameAccount();   // Handling 'Add Game Account' option
                case 2 -> addSubscription(); // Handling 'Add Subscription' option
                default -> System.out.println("Invalid option. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    // Editing an existing resource
    private void editResource() {
        System.out.println("\n\n--- Edit Resource ---");

        // Finding the resource to edit
        Resource resource = findResource();
        if (resource == null) return;

        System.out.println("\nEditing Resource: " + resource.getName());
        if (resource instanceof GameAccount gameAccount) {
            // Updating attributes specific to a GameAccount
            updateResourceAttributes(gameAccount);
            gameAccount.setGamePlatform(promptInput("Enter new Platform (current: " + gameAccount.getGamePlatform() + "): ", gameAccount.getGamePlatform()));
        } else if (resource instanceof Subscription subscription) {
            // Updating attributes specific to a Subscription
            updateResourceAttributes(subscription);
            subscription.setSubscriptionType(promptInput("Enter new Subscription Type (current: " + subscription.getSubscriptionType() + "): ", subscription.getSubscriptionType()));
        }

        resourceService.saveResource(resource); // Saving the updated resource
        System.out.println("Resource updated successfully.");
    }

    // Removing a resource
    private void removeResource() {
        System.out.println("\n--- Remove Resource ---");

        // Finding the resource to remove
        Resource resource = findResource();
        if (resource == null) return;

        resourceService.deleteResource(resource.getId()); // Deleting the resource via the service
        System.out.println("Resource removed successfully.");
    }

    // Adding a new GameAccount resource
    private void addGameAccount() {
        System.out.println("\n\n--- Add Game Account ---");

        // Creating a new GameAccount object based on user input
        GameAccount gameAccount = new GameAccount(
                promptInput("Enter Game Name: "),
                promptInput("Enter Username: "),
                promptInput("Enter Password: "),
                promptInput("Enter Platform (e.g., PC, Xbox, PlayStation): ")
        );

        resourceService.addResource(gameAccount, user); // Saving the resource and associating it with the user
        System.out.println("Game account added successfully!");
    }

    // Adding a new Subscription resource
    private void addSubscription() {
        System.out.println("\n\n--- Add Subscription ---");

        // Creating a new Subscription object based on user input
        Subscription subscription = new Subscription(
                promptInput("Enter Subscription Name (e.g., Netflix, Spotify): "),
                promptInput("Enter Username: "),
                promptInput("Enter Password: "),
                promptInput("Enter Subscription Type (e.g., Streaming, Magazine): ")
        );

        resourceService.addResource(subscription, user); // Saving the resource and associating it with the user
        System.out.println("Subscription added successfully!");
    }

    // Finding a resource by name
    private Resource findResource() {
        System.out.print("\nEnter the name of the resource: ");
        String resourceName = scanner.nextLine();
        Resource resource = resourceService.findResourceByNameAndUser(resourceName, user);
        if (resource == null) {
            System.out.println("No resource found with the name: " + resourceName);
        }
        return resource;
    }

    // Updating common attributes of a resource
    private void updateResourceAttributes(Resource resource) {
        resource.setName(promptInput("Enter new Name (current: " + resource.getName() + "): ", resource.getName()));
        resource.setUsername(promptInput("Enter new Username (current: " + resource.getUsername() + "): ", resource.getUsername()));
        resource.setPassword(promptInput("Enter new Password (current: " + resource.getPassword() + "): ", resource.getPassword()));
    }

    // Prompting user for input with a message
    private String promptInput(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    // Prompting user for input with a default value
    private String promptInput(String message, String currentValue) {
        System.out.print(message);
        String input = scanner.nextLine();
        return input.isEmpty() ? currentValue : input; // If input is empty, returns the current value
    }


}
