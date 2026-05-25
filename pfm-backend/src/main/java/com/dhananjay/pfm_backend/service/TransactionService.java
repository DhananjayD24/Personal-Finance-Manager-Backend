package com.dhananjay.pfm_backend.service;

import com.dhananjay.pfm_backend.dto.request.TransactionRequest;
import com.dhananjay.pfm_backend.dto.response.TransactionResponse;
import com.dhananjay.pfm_backend.dto.request.UpdateTransactionRequest;
import com.dhananjay.pfm_backend.dto.response.MessageResponse;
import java.time.LocalDate;

import java.util.List;

public interface TransactionService {

    TransactionResponse createTransaction(
            TransactionRequest request,
            Long userId);

    List<TransactionResponse> getAllTransactions(
        Long userId,
        LocalDate startDate,
        LocalDate endDate,
        Long categoryId);

    TransactionResponse updateTransaction(
        Long transactionId,
        UpdateTransactionRequest request,
        Long userId);

    MessageResponse deleteTransaction(
        Long transactionId,
        Long userId);
}