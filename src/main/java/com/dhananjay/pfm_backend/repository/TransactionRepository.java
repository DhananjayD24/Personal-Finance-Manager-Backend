package com.dhananjay.pfm_backend.repository;

import com.dhananjay.pfm_backend.entity.Transaction;
import com.dhananjay.pfm_backend.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

import java.util.Optional;

import com.dhananjay.pfm_backend.entity.Category;

import java.math.BigDecimal;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUser(User user);

    List<Transaction> findByUserAndDateBetween(
            User user,
            LocalDate startDate,
            LocalDate endDate);

    Optional<Transaction> findByIdAndUser(
            Long id,
            User user);

    List<Transaction> findByUserAndCategory(
            User user,
            Category category);

    List<Transaction> findByUserAndCategoryAndDateBetween(
            User user,
            Category category,
            LocalDate startDate,
            LocalDate endDate);

    List<Transaction> findByUserAndDateGreaterThanEqual(
        User user,
        LocalDate date);
}