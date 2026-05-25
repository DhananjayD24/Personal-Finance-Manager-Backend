package com.dhananjay.pfm_backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank(message = "Email is required")

    @Email(message = "Invalid email format")
    private String username;

    @NotBlank(message = "Password is required")

    @Size(
            min = 6,
            message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Phone number is required")

    @Pattern(
            regexp = "^[+]?[0-9]{10,15}$",
            message = "Phone number must be valid")
    private String phoneNumber;
}