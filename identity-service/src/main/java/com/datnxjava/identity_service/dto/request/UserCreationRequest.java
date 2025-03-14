package com.datnxjava.identity_service.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

//use lombok @Data to shorten getter & setter , constructor
@Data
@NoArgsConstructor // create constructor without parameter
@AllArgsConstructor //create constructor full  parameter
@Builder // create object instead of using constructor
@FieldDefaults(level = AccessLevel.PRIVATE) //all field by default is private (private String a -> String a)
public class UserCreationRequest {

    @Size(min = 3, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, message = "PASSWORD_INVALID") //annotation to make sure password has enough required length
    String password;

    String firstName;
    String lastName;
    LocalDate dob;


}
