package com.eric.onlineresourcemanagementsys.resource_management;

import com.eric.onlineresourcemanagementsys.Entity.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        List<Resource> resources = resourceManager.getResources();
        assertEquals(1, resources.size(), "Resource list should contain one resource");
        assertEquals(gameAccount, resources.get(0), "The added resource should match");
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

    @Test
    void testGetNonExistentResource() {
        // Attempt to retrieve a resource that doesn't exist
        Resource resource = resourceManager.getResource("NonExistentResource");

        // Verify that null is returned
        assertEquals(null, resource, "Retrieving a non-existent resource should return null");
    }

    @Test
    void testRemoveResource() {
        // Add a resource
        Subscription subscription = new Subscription("Netflix", "user2", "pass2", "Streaming");
        resourceManager.addResource(subscription);

        // Remove the resource
        resourceManager.removeResource(subscription);

        // Verify the resource list is empty
        List<Resource> resources = resourceManager.getResources();
        assertEquals(0, resources.size(), "Resource list should be empty after removing the resource");

        // Verify the resource can no longer be retrieved
        Resource retrievedResource = resourceManager.getResource("Netflix");
        assertEquals(null, retrievedResource, "Removed resource should not be retrievable");
    }
}
