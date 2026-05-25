package com.dhananjay.pfm_backend.dto.response;

import com.dhananjay.pfm_backend.enums.TransactionType;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {

    private Long id;

    private BigDecimal amount;

    private LocalDate date;

    private String category;

    private TransactionType type;

    private String description;
}