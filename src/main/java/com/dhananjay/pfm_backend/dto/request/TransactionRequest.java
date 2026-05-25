package com.dhananjay.pfm_backend.dto.request;

import com.dhananjay.pfm_backend.enums.TransactionType;

import jakarta.validation.constraints.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.PastOrPresent;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Date is required")

    @PastOrPresent(message = "Date cannot be future")
    private LocalDate date;

    private Long categoryId;

    private String category;

    // SUPPORT TEST SCRIPT
    private TransactionType type;

    private String description;
}