package com.dhananjay.pfm_backend.controller;

import com.dhananjay.pfm_backend.dto.request.RegisterRequest;
import com.dhananjay.pfm_backend.dto.response.AuthResponse;

import com.dhananjay.pfm_backend.service.AuthService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.dhananjay.pfm_backend.dto.request.LoginRequest;
import com.dhananjay.pfm_backend.dto.response.MessageResponse;

import jakarta.servlet.http.HttpSession;

import com.dhananjay.pfm_backend.dto.response.UserResponse;
import com.dhananjay.pfm_backend.exception.UnauthorizedException;

@RestController
@RequestMapping("/api/auth")

@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        AuthResponse response = authService.register(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<MessageResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpSession session) {

        MessageResponse response = authService.login(request, session);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(
            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {

            throw new UnauthorizedException(
                    "User not logged in");
        }

        UserResponse response = authService.getCurrentUser(userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(
            HttpSession session) {

        MessageResponse response = authService.logout(session);

        return ResponseEntity.ok(response);
    }
}