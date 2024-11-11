package com.example.institutionapp.services;

import com.example.institutionapp.entities.InstitutionUser;
import com.example.institutionapp.exceptions.InvalidUsernamePasswordException;
import com.example.institutionapp.exceptions.UserAlreadyExistsException;
import com.example.institutionapp.exceptions.UserNotFoundException;
import com.example.institutionapp.models.auth.CreateUserRequest;
import com.example.institutionapp.repositories.InstitutionUserRepository;
import com.example.institutionapp.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final InstitutionUserRepository institutionUserRepository;
    private final PasswordEncoder passwordEncoder;

    public String login(String username, String password) {
        log.info("Attempting login for user: {}", username);
        try {
            if(this.institutionUserRepository.findByUsername(username).isEmpty()) {
                String message = "User " + username + " not found";
                throw new UserNotFoundException(message);
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            return jwtTokenUtil.generateToken(username);
        } catch (BadCredentialsException e) {
            throw new InvalidUsernamePasswordException("Invalid username or password");
        } catch (InternalAuthenticationServiceException e) {
            throw new RuntimeException("Internal auth service error: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void createUser(CreateUserRequest createUserRequest) {
        log.info("Attempting to create user: {}", createUserRequest.getUsername());

        InstitutionUser user = this.mapFromCreateUser(createUserRequest);

        if(this.institutionUserRepository.findByUsername(user.getUsername()).isPresent()) {
            String message = "User " + user.getUsername() + " already exists";
            throw new UserAlreadyExistsException(message);
        }

        this.institutionUserRepository.save(user);
    }

    private InstitutionUser mapFromCreateUser(CreateUserRequest createUserRequest) {
        return new InstitutionUser(
                createUserRequest.getUsername(),
                this.passwordEncoder.encode(createUserRequest.getPassword())
        );
    }
}
