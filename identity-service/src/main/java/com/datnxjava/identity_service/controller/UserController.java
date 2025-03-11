package com.datnxjava.identity_service.controller;

import com.datnxjava.identity_service.dto.request.ApiResponse;
import com.datnxjava.identity_service.dto.request.UserCreationRequest;
import com.datnxjava.identity_service.dto.request.UserUpdateRequest;
import com.datnxjava.identity_service.dto.response.UserResponse;
import com.datnxjava.identity_service.entity.User;
import com.datnxjava.identity_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users") //declare all request for this path
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE,makeFinal = true)
public class UserController {
    UserService userService;

    //Create User
    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) { //map data from request into object = @RequestBody , valid UserCreationRequest with defined rule by @Valid
        //return api response
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    //Get All Users
    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUser())
                .build();
    }

    // Get User by id
    @GetMapping("/{userId}") //if declare @GetMapping("/{userId}"), it will map value into String userId by @PathVariable
    ApiResponse<UserResponse>  getUser(@PathVariable String userId) {
        return ApiResponse.<UserResponse>builder()
                        .result(userService.getUserById(userId))
                .build();
    }

    //Update User
    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId,request))
                .build();
    }


    //Delete User
    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId) { //string func because just need to send back string notification about deleting user
        userService.deleteUser(userId);
        return "User has ben deleted";
    }
}
