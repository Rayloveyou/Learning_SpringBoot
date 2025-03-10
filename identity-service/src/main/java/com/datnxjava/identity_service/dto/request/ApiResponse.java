package com.datnxjava.identity_service.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL) //will remove null field when return
public class ApiResponse <T> {
     int code = 1000; // code of api, set default = 1000 means success
     String message; //api message
     T result ; //declare T type because this type can be changed flexibly due to each API response



}
