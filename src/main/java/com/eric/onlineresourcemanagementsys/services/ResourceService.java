package com.eric.onlineresourcemanagementsys.services;

import com.eric.onlineresourcemanagementsys.Entity.Resource;
import com.eric.onlineresourcemanagementsys.Entity.User;
import com.eric.onlineresourcemanagementsys.repos.ResourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResourceService {
    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }


    // Add a new resource
    public void addResource(Resource resource, User user) {
        resource.setUser(user);
        resourceRepository.save(resource);
    }

    // Delete a resource
    public void deleteResource(int id) {
        resourceRepository.deleteById(id);
    }

    public Resource findResourceByNameAndUser(String resourceName, User user) {
        return resourceRepository.findResourceByNameAndUser(resourceName, user);
    }

    public void saveResource(Resource resource) {
        resourceRepository.save(resource);
    }

    // Retrieve all resources for a user grouping Resources By Type
    public Map<String, List<Resource>> groupResourcesByType(int userId) {
        List<Resource> resources = resourceRepository.findAllByUserId(userId);

        // Group resources by their class type (e.g., GameAccount, Subscription)
        return resources.stream()
                .collect(Collectors.groupingBy(resource -> resource.getClass().getSimpleName()));
    }
}
