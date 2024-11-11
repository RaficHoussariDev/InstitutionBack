package com.example.institutionapp.controllers;

import com.example.institutionapp.models.auth.AuthRequest;
import com.example.institutionapp.models.auth.AuthResponse;
import com.example.institutionapp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest authRequest) {
        String token = this.authService.login(authRequest.getUsername(), authRequest.getPassword());


        return new ResponseEntity<>(
                new AuthResponse(token),
                HttpStatus.OK
        );
    }
}
