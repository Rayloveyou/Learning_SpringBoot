package com.datnxjava.identity_service.service;

import com.datnxjava.identity_service.dto.request.UserCreationRequest;
import com.datnxjava.identity_service.dto.request.UserUpdateRequest;
import com.datnxjava.identity_service.entity.User;
import com.datnxjava.identity_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// this layer will call to repository service
@Service
public class UserService {
    // dependency injection for user repository
    @Autowired
    UserRepository userRepository;

    public User createUser(UserCreationRequest request) {
        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setfirstName(request.getfirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());

        //save user info into database
        return userRepository.save(user);
    }

    public List<User> getUser(){
        return userRepository.findAll(); //findAll func to get all user
    }

    public User getUserById(String id){
        return userRepository.findById(id) //findAll func to get specific user
                .orElseThrow(() -> new RuntimeException("User not found")); //if not found user, return "User not found"
    }

    public User updateUser(String userId, UserUpdateRequest request) {
        User user = getUserById(userId); //get userId to update

        //update info to user
        user.setPassword(request.getPassword());
        user.setfirstName(request.getfirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());

        //save back user into db
        return userRepository.save(user);
    }

    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }
}
