package com.dhananjay.pfm_backend.dto.request;

import com.dhananjay.pfm_backend.enums.TransactionType;

import jakarta.validation.constraints.Positive;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateTransactionRequest {

    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    private Long categoryId;

    private String category;

    private TransactionType type;

    // SHOULD BE IGNORED
    private LocalDate date;

    private String description;
}