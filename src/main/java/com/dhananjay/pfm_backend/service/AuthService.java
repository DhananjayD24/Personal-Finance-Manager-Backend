package com.dhananjay.pfm_backend.service;

import com.dhananjay.pfm_backend.dto.request.LoginRequest;
import com.dhananjay.pfm_backend.dto.request.RegisterRequest;

import com.dhananjay.pfm_backend.dto.response.AuthResponse;
import com.dhananjay.pfm_backend.dto.response.MessageResponse;

import jakarta.servlet.http.HttpSession;

import com.dhananjay.pfm_backend.dto.response.UserResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    MessageResponse login(
            LoginRequest request,
            HttpSession session);

    UserResponse getCurrentUser(Long userId);
    MessageResponse logout(HttpSession session);
}
