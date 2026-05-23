package com.dhananjay.pfm_backend.service;

import com.dhananjay.pfm_backend.dto.request.LoginRequest;
import com.dhananjay.pfm_backend.dto.request.RegisterRequest;

import com.dhananjay.pfm_backend.dto.response.AuthResponse;
import com.dhananjay.pfm_backend.dto.response.MessageResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    MessageResponse login(LoginRequest request);
}