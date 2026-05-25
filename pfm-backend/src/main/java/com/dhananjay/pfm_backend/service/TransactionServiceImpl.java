package com.dhananjay.pfm_backend.service;

import com.dhananjay.pfm_backend.dto.request.TransactionRequest;
import com.dhananjay.pfm_backend.dto.request.UpdateTransactionRequest;
import com.dhananjay.pfm_backend.dto.response.TransactionResponse;

import com.dhananjay.pfm_backend.entity.Category;
import com.dhananjay.pfm_backend.entity.Transaction;
import com.dhananjay.pfm_backend.entity.User;

import com.dhananjay.pfm_backend.exception.ResourceNotFoundException;
import com.dhananjay.pfm_backend.exception.UnauthorizedException;

import com.dhananjay.pfm_backend.repository.CategoryRepository;
import com.dhananjay.pfm_backend.repository.TransactionRepository;
import com.dhananjay.pfm_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

import com.dhananjay.pfm_backend.dto.response.MessageResponse;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl
        implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    @Override
    public TransactionResponse createTransaction(
            TransactionRequest request,
            Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found"));

        Category category = categoryRepository.findById(
                request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found"));

        // CATEGORY ACCESS VALIDATION
        if (category.getUser() != null &&
                !category.getUser().getId().equals(userId)) {

            throw new UnauthorizedException(
                    "You cannot use this category");
        }

        Transaction transaction = Transaction.builder()
                .amount(request.getAmount())
                .date(request.getDate())
                .description(request.getDescription())
                .category(category)
                .user(user)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionResponse.builder()
                .id(savedTransaction.getId())
                .amount(savedTransaction.getAmount())
                .date(savedTransaction.getDate())
                .category(savedTransaction
                        .getCategory()
                        .getName())
                .type(savedTransaction
                        .getCategory()
                        .getType())
                .description(savedTransaction.getDescription())
                .build();
    }

    @Override
    public List<TransactionResponse> getAllTransactions(
            Long userId,
            java.time.LocalDate startDate,
            java.time.LocalDate endDate,
            Long categoryId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found"));

        List<Transaction> transactions;

        // FILTER: CATEGORY + DATE
        if (categoryId != null &&
                startDate != null &&
                endDate != null) {

            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Category not found"));

            transactions = transactionRepository
                    .findByUserAndCategoryAndDateBetween(
                            user,
                            category,
                            startDate,
                            endDate);

        }

        // FILTER: DATE ONLY
        else if (startDate != null &&
                endDate != null) {

            transactions = transactionRepository
                    .findByUserAndDateBetween(
                            user,
                            startDate,
                            endDate);
        }

        // FILTER: CATEGORY ONLY
        else if (categoryId != null) {

            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Category not found"));

            transactions = transactionRepository
                    .findByUserAndCategory(
                            user,
                            category);
        }

        // NO FILTER
        else {

            transactions = transactionRepository.findByUser(user);
        }

        return transactions.stream()
                .map(transaction -> TransactionResponse.builder()
                        .id(transaction.getId())
                        .amount(transaction.getAmount())
                        .date(transaction.getDate())
                        .category(transaction
                                .getCategory()
                                .getName())
                        .type(transaction
                                .getCategory()
                                .getType())
                        .description(
                                transaction.getDescription())
                        .build())
                .toList();
    }

    @Override
    public TransactionResponse updateTransaction(
            Long transactionId,
            UpdateTransactionRequest request,
            Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found"));

        Transaction transaction = transactionRepository.findByIdAndUser(
                transactionId,
                user)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Transaction not found"));

        Category category = categoryRepository.findById(
                request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found"));

        // CATEGORY OWNERSHIP CHECK
        if (category.getUser() != null &&
                !category.getUser().getId().equals(userId)) {

            throw new UnauthorizedException(
                    "You cannot use this category");
        }

        // UPDATE FIELDS
        transaction.setAmount(request.getAmount());

        transaction.setCategory(category);

        transaction.setDescription(
                request.getDescription());

        Transaction updatedTransaction = transactionRepository.save(transaction);

        return TransactionResponse.builder()
                .id(updatedTransaction.getId())
                .amount(updatedTransaction.getAmount())
                .date(updatedTransaction.getDate())
                .category(updatedTransaction
                        .getCategory()
                        .getName())
                .type(updatedTransaction
                        .getCategory()
                        .getType())
                .description(
                        updatedTransaction.getDescription())
                .build();
    }

    @Override
    public MessageResponse deleteTransaction(
            Long transactionId,
            Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found"));

        Transaction transaction = transactionRepository.findByIdAndUser(
                transactionId,
                user)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Transaction not found"));

        transactionRepository.delete(transaction);

        return MessageResponse.builder()
                .message("Transaction deleted successfully")
                .build();
    }
}