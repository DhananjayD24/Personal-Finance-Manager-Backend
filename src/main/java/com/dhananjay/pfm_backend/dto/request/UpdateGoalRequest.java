package com.dhananjay.pfm_backend.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateGoalRequest {

    @Positive(message = "Target amount must be positive")
    private BigDecimal targetAmount;

    @Future(message = "Target date must be future")
    private LocalDate targetDate;
}