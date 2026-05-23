package com.dhananjay.pfm_backend.service;

import com.dhananjay.pfm_backend.dto.request.RegisterRequest;
import com.dhananjay.pfm_backend.dto.response.AuthResponse;

import com.dhananjay.pfm_backend.entity.User;

import com.dhananjay.pfm_backend.exception.DuplicateResourceException;

import com.dhananjay.pfm_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dhananjay.pfm_backend.dto.request.LoginRequest;
import com.dhananjay.pfm_backend.dto.response.MessageResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.dhananjay.pfm_backend.exception.UnauthorizedException;

import org.springframework.security.core.AuthenticationException;

@Service
@RequiredArgsConstructor

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Override
    @SuppressWarnings("null")
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {

            throw new DuplicateResourceException(
                    "Email already registered");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .build();

        User savedUser = userRepository.save(user);

        return AuthResponse.builder()
                .message("User registered successfully")
                .userId(savedUser.getId())
                .build();
    }

    @Override
    public MessageResponse login(LoginRequest request) {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()));

        } catch (AuthenticationException ex) {

            throw new UnauthorizedException(
                    "Invalid username or password");
        }

        return MessageResponse.builder()
                .message("Login successful")
                .build();
    }
}