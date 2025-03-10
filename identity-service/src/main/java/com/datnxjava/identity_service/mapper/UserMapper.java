package com.datnxjava.identity_service.mapper;

import ch.qos.logback.core.model.ComponentModel;
import com.datnxjava.identity_service.dto.request.UserCreationRequest;
import com.datnxjava.identity_service.dto.request.UserUpdateRequest;
import com.datnxjava.identity_service.dto.response.UserResponse;
import com.datnxjava.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

//declare annotation @Mapper , create spring bean from this interface to autowired in other classes
@Mapper(componentModel = "spring")
public interface UserMapper {

    //create mapper request to user
    //mapstruct will automatically mapping fields with the same name between UserCreationRequest and User
    User toUser(UserCreationRequest request); //nhận về 1 parameter request theo kiểu UserCreationRequest -> Trả về class User

    //use @mapping(source = "", target = "") when you want map different field name
//    @Mapping(source = "firstName", target = "lastName")
    //use @mapping(target = "", ignore = "") when you don't want map that field
//    @Mapping(target = "firstName", ignore = true )
    //create mapper user to response
    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request); //=> map UserUpdateRequest into User
}
