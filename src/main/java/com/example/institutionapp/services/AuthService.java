package com.example.institutionapp.services;

import com.example.institutionapp.exceptions.InvalidUsernamePasswordException;
import com.example.institutionapp.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public String login(String username, String password) {
        log.info("Attempting login for user: {}", username);
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            return jwtTokenUtil.generateToken(username);
        } catch (BadCredentialsException e) {
            throw new InvalidUsernamePasswordException("Invalid username or password");
        } catch (InternalAuthenticationServiceException e) {
            throw new RuntimeException("Internal auth service error: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during login", e);
        }
    }
}
