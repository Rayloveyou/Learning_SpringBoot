package com.datnxjava.identity_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

//use lombok @Data to shorten getter & setter , constructor
@Data
@NoArgsConstructor // create constructor without parameter
@AllArgsConstructor //create constructor full  parameter
@Builder // create object instead of using constructor
@FieldDefaults(level = AccessLevel.PRIVATE) //all field by default is private (private String a -> String a)
public class AuthenticationResponse {
    String token;
    boolean authenticated;
}
