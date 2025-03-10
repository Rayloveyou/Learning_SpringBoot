package com.datnxjava.identity_service.service;

import com.datnxjava.identity_service.dto.request.UserCreationRequest;
import com.datnxjava.identity_service.dto.request.UserUpdateRequest;
import com.datnxjava.identity_service.dto.response.UserResponse;
import com.datnxjava.identity_service.entity.User;
import com.datnxjava.identity_service.exception.AppException;
import com.datnxjava.identity_service.exception.ErrorCode;
import com.datnxjava.identity_service.mapper.UserMapper;
import com.datnxjava.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

// this layer will call to repository service
@Service
@Builder
@RequiredArgsConstructor //create a constructor for all var you declare as "final"
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) //default var is private final
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    public UserResponse createUser(UserCreationRequest request) {
        // check if user existed
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED); //throw user existed exception

        //use userMapper to map request info into User object
        User user = userMapper.toUser(request);
        //Brcypt password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10); //con số tùy chỉnh theo nhu cầu, càng lớn mã hóa càng khó nhưng mất nhiều tgian cho performance
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        //save user info into database
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getUser() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();//findAll func to get all user
    }

    public UserResponse getUserById(String id) {
        return userMapper.toUserResponse(userRepository.findById(id) //findAll func to get specific user
                .orElseThrow(() -> new RuntimeException("User not found"))); //if not found user, return "User not found"
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")); //get userId to update


        userMapper.updateUser(user, request); //mapstruct, map request info into user

        //save back user into db
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
