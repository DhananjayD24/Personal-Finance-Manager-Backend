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
            min = 8,
            message = "Password must be at least 8 characters")

    @Pattern(
            regexp =
                    "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",

            message =
                    "Password must contain uppercase, lowercase and number")
    private String password;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Phone number is required")

    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must be exactly 10 digits")
    private String phoneNumber;
}