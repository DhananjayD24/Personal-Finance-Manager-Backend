package com.dhananjay.pfm_backend.controller;

import com.dhananjay.pfm_backend.dto.request.TransactionRequest;
import com.dhananjay.pfm_backend.dto.response.TransactionResponse;

import com.dhananjay.pfm_backend.exception.UnauthorizedException;

import com.dhananjay.pfm_backend.service.TransactionService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.dhananjay.pfm_backend.dto.request.UpdateTransactionRequest;
import com.dhananjay.pfm_backend.dto.response.MessageResponse;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/transactions")

@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody TransactionRequest request,
            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {

            throw new UnauthorizedException(
                    "User not logged in");
        }

        TransactionResponse response = transactionService.createTransaction(
                request,
                userId);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions(

            @RequestParam(required = false) LocalDate startDate,

            @RequestParam(required = false) LocalDate endDate,

            @RequestParam(required = false) Long categoryId,

            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {

            throw new UnauthorizedException(
                    "User not logged in");
        }

        List<TransactionResponse> response = transactionService.getAllTransactions(
                userId,
                startDate,
                endDate,
                categoryId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTransactionRequest request,
            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {

            throw new UnauthorizedException(
                    "User not logged in");
        }

        TransactionResponse response = transactionService.updateTransaction(
                id,
                request,
                userId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteTransaction(
            @PathVariable Long id,
            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {

            throw new UnauthorizedException(
                    "User not logged in");
        }

        MessageResponse response = transactionService.deleteTransaction(
                id,
                userId);

        return ResponseEntity.ok(response);
    }
}