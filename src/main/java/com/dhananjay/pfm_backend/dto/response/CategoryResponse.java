package com.dhananjay.pfm_backend.dto.response;

import com.dhananjay.pfm_backend.enums.TransactionType;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {

    private Long id;

    private String name;

    private TransactionType type;

    private boolean isCustom;
}