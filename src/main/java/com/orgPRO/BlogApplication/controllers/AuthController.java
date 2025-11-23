
package com.orgPRO.BlogApplication.controllers;

import com.orgPRO.BlogApplication.domain.dtos.AuthResponse;
import com.orgPRO.BlogApplication.domain.dtos.LoginRequest;
import com.orgPRO.BlogApplication.services.AuthenticationService;
import com.orgPRO.BlogApplication.services.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/api/v1/auth/login")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationServiceImpl authenticationService;

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {

        System.out.println("i am login");
        UserDetails authenticate = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        String token = authenticationService.generateToken(authenticate);
        AuthResponse build = AuthResponse.builder()
                .token(token)
                .expiresIn(86400)
                .build();

        return new ResponseEntity<>(build, HttpStatus.OK);
    }

}

