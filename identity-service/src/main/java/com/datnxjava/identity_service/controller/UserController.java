package com.datnxjava.identity_service.controller;

import com.datnxjava.identity_service.dto.request.UserCreationRequest;
import com.datnxjava.identity_service.dto.request.UserUpdateRequest;
import com.datnxjava.identity_service.entity.User;
import com.datnxjava.identity_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users") //declare all request for this path
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    User createUser(@RequestBody UserCreationRequest request){ //map data from request into object = @RequestBody
       return userService.createUser(request);
    }

    @GetMapping
    List<User> getUsers(){
        return userService.getUser();
    }

    @GetMapping("/{userId}")
    User getUser(@PathVariable String userId){ //if declare @GetMapping("/{userId}"), it will map value into String userId by @PathVariable
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    User updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId){ //string func because just need to send back string notification about deleting user
         userService.deleteUser(userId);
         return "User has ben deleted";
    }
}
