package com.datnxjava.identity_service.repository;

import com.datnxjava.identity_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// this layer will call to entity service
//need extend JpaRepository<(Entity),(Type of Primary key)
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    //func search exist username
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

}
