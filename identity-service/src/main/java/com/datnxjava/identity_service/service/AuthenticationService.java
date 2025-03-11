package com.datnxjava.identity_service.service;

import com.datnxjava.identity_service.dto.request.AuthenticationRequest;
import com.datnxjava.identity_service.dto.response.AuthenticationResponse;
import com.datnxjava.identity_service.exception.AppException;
import com.datnxjava.identity_service.exception.ErrorCode;
import com.datnxjava.identity_service.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor //auto wired cac bean
@Slf4j //giúp log trong app để check dễ hơn
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal //đánh dấu để k inject vào constructor
    protected static final String SIGNER_KEY = "bRrxVZeHbBtan18BBaE15VRBfD28x8RDVaksO0LagSOYTdizssJUnxH5BKq8R14a";

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);


        //var to check user login success or not
        boolean authenticated = passwordEncoder.matches(request.getPassword(),
                user.getPassword()); // so sanh password nhap vao va password cua user

        if (!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        //if authen
        var token = generateToken(request.getUsername());

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();

    }


    //method tạo token, token string -> string method
    private String generateToken(String username) { //cân thông tin username

        //header + payload
        //dùng thuật toán HS512
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        //data trong body: claim
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("datnxdevops.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli() //hết hạn sau 1 giờ
                ))
                .claim("customClaim", "Custom")
                .build();

        //tạo payload cho token với định dạng json
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        //jsonweb token
        JWSObject jwsObject = new JWSObject(header, payload);

        //ký token
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }
}
