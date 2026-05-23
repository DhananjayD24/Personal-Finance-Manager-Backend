package com.dhananjay.pfm_backend.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AuthResponse {

    private String message;

    private Long userId;
}