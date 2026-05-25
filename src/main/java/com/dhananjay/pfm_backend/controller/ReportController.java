package com.dhananjay.pfm_backend.controller;

import com.dhananjay.pfm_backend.dto.response.MonthlyReportResponse;
import com.dhananjay.pfm_backend.dto.response.YearlyReportResponse;

import com.dhananjay.pfm_backend.exception.UnauthorizedException;

import com.dhananjay.pfm_backend.service.ReportService;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")

@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/monthly/{year}/{month}")
    public ResponseEntity<MonthlyReportResponse>
    getMonthlyReport(

            @PathVariable int year,

            @PathVariable int month,

            HttpSession session) {

        Long userId =
                (Long) session.getAttribute("userId");

        if (userId == null) {

            throw new UnauthorizedException(
                    "User not logged in");
        }

        MonthlyReportResponse response =
                reportService.getMonthlyReport(
                        year,
                        month,
                        userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/yearly/{year}")
    public ResponseEntity<YearlyReportResponse>
    getYearlyReport(

            @PathVariable int year,

            HttpSession session) {

        Long userId =
                (Long) session.getAttribute("userId");

        if (userId == null) {

            throw new UnauthorizedException(
                    "User not logged in");
        }

        YearlyReportResponse response =
                reportService.getYearlyReport(
                        year,
                        userId);

        return ResponseEntity.ok(response);
    }
}