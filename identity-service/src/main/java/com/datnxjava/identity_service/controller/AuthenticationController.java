package com.datnxjava.identity_service.controller;

import com.datnxjava.identity_service.dto.request.ApiResponse;
import com.datnxjava.identity_service.dto.request.AuthenticationRequest;
import com.datnxjava.identity_service.dto.response.AuthenticationResponse;
import com.datnxjava.identity_service.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor //auto wired cac bean
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(1000)
                .result(result)
                .build();
    }
}
