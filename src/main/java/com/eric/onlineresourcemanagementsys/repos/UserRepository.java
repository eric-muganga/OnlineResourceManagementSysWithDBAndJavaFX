package com.eric.onlineresourcemanagementsys.repos;

import com.eric.onlineresourcemanagementsys.Entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GenericRepository<User, Integer> {
    User findByUsername(String username);
}
