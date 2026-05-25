package com.dhananjay.pfm_backend.service;

import com.dhananjay.pfm_backend.dto.response.MonthlyReportResponse;
import com.dhananjay.pfm_backend.dto.response.YearlyReportResponse;

import com.dhananjay.pfm_backend.entity.Transaction;
import com.dhananjay.pfm_backend.entity.User;

import com.dhananjay.pfm_backend.enums.TransactionType;

import com.dhananjay.pfm_backend.exception.ResourceNotFoundException;

import com.dhananjay.pfm_backend.repository.TransactionRepository;
import com.dhananjay.pfm_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl
        implements ReportService {

    private final TransactionRepository transactionRepository;

    private final UserRepository userRepository;

    @Override
    public MonthlyReportResponse getMonthlyReport(
            int year,
            int month,
            Long userId) {

        LocalDate startDate =
                LocalDate.of(year, month, 1);

        LocalDate endDate =
                startDate.withDayOfMonth(
                        startDate.lengthOfMonth());

        return buildReport(
                startDate,
                endDate,
                year,
                month,
                userId);
    }

    @Override
    public YearlyReportResponse getYearlyReport(
            int year,
            Long userId) {

        LocalDate startDate =
                LocalDate.of(year, 1, 1);

        LocalDate endDate =
                LocalDate.of(year, 12, 31);

        MonthlyReportResponse report =
                buildReport(
                        startDate,
                        endDate,
                        year,
                        0,
                        userId);

        return YearlyReportResponse.builder()
                .year(year)
                .totalIncome(report.getTotalIncome())
                .totalExpenses(report.getTotalExpenses())
                .netSavings(report.getNetSavings())
                .build();
    }

    // =========================
    // COMMON REPORT LOGIC
    // =========================

    private MonthlyReportResponse buildReport(
            LocalDate startDate,
            LocalDate endDate,
            int year,
            int month,
            Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        List<Transaction> transactions =
                transactionRepository
                        .findByUserAndDateBetween(
                                user,
                                startDate,
                                endDate);

        Map<String, BigDecimal> incomeMap =
                new HashMap<>();

        Map<String, BigDecimal> expenseMap =
                new HashMap<>();

        BigDecimal totalIncome =
                BigDecimal.ZERO;

        BigDecimal totalExpense =
                BigDecimal.ZERO;

        for (Transaction transaction : transactions) {

            String categoryName =
                    transaction.getCategory().getName();

            BigDecimal amount =
                    transaction.getAmount();

            // INCOME
            if (transaction.getCategory().getType()
                    == TransactionType.INCOME) {

                incomeMap.put(
                        categoryName,

                        incomeMap.getOrDefault(
                                categoryName,
                                BigDecimal.ZERO)
                                .add(amount));

                totalIncome =
                        totalIncome.add(amount);

            }

            // EXPENSE
            else {

                expenseMap.put(
                        categoryName,

                        expenseMap.getOrDefault(
                                categoryName,
                                BigDecimal.ZERO)
                                .add(amount));

                totalExpense =
                        totalExpense.add(amount);
            }
        }

        BigDecimal netSavings =
                totalIncome.subtract(totalExpense);

        return MonthlyReportResponse.builder()
                .month(month)
                .year(year)
                .totalIncome(incomeMap)
                .totalExpenses(expenseMap)
                .netSavings(netSavings)
                .build();
    }
}