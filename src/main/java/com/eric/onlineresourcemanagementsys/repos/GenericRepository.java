package com.eric.onlineresourcemanagementsys.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean //this used to indicate that a repository interface should not be instantiated as a Spring Data repository bean
public interface GenericRepository<T, ID> extends JpaRepository<T, ID> {
}
