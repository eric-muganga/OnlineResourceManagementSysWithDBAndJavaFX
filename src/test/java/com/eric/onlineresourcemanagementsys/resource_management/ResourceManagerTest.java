package com.eric.onlineresourcemanagementsys.resource_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResourceManagerTest {
    private ResourceManager resourceManager;

    @BeforeEach
    void setUp() {
        resourceManager = new ResourceManager();
    }

    @Test
    void testAddResource() {
        // Create a new GameAccount resource
        GameAccount gameAccount = new GameAccount("Game1", "user1", "pass1", "PC");
        resourceManager.addResource(gameAccount);

        // Verify that the resource was added
        assertEquals(1, resourceManager.getResources().size(), "Resource list should contain one resource");
        assertEquals(gameAccount, resourceManager.getResources().getFirst(), "The added resource should match");
    }

    @Test
    void testGetResource() {
        // Add a resource and retrieve it by name
        Subscription subscription = new Subscription("Netflix", "user2", "pass2", "Streaming");
        resourceManager.addResource(subscription);

        // Verify that the correct resource is retrieved
        Resource retrievedResource = resourceManager.getResource("Netflix");
        assertNotNull(retrievedResource, "Resource should be found by name");
        assertEquals(subscription, retrievedResource, "Retrieved resource should match the added resource");
    }
}
