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
            @Valid @RequestBody LoginRequest request) {

        MessageResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }
}