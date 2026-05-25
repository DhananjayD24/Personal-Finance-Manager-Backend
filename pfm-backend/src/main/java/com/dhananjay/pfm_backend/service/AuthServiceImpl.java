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

import jakarta.servlet.http.HttpSession;

import com.dhananjay.pfm_backend.dto.response.UserResponse;

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
        public MessageResponse login(
                        LoginRequest request,
                        HttpSession session) {

                try {

                        authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                        request.getUsername(),
                                                        request.getPassword()));

                        // FIND USER
                        User user = userRepository.findByUsername(
                                        request.getUsername())
                                        .orElseThrow(() -> new UnauthorizedException(
                                                        "User not found"));

                        // STORE USER ID IN SESSION
                        session.setAttribute("userId", user.getId());

                } catch (AuthenticationException ex) {

                        throw new UnauthorizedException(
                                        "Invalid username or password");
                }

                return MessageResponse.builder()
                                .message("Login successful")
                                .build();
        }

        @Override
        public UserResponse getCurrentUser(Long userId) {

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new UnauthorizedException("User not found"));

                return UserResponse.builder()
                                .id(user.getId())
                                .username(user.getUsername())
                                .fullName(user.getFullName())
                                .phoneNumber(user.getPhoneNumber())
                                .build();
        }

        @Override
        public MessageResponse logout(HttpSession session) {

                session.invalidate();

                return MessageResponse.builder()
                                .message("Logout successful")
                                .build();
        }
}