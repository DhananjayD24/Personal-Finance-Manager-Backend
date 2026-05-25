package com.dhananjay.pfm_backend.service;

import com.dhananjay.pfm_backend.dto.response.MonthlyReportResponse;
import com.dhananjay.pfm_backend.dto.response.YearlyReportResponse;

public interface ReportService {

    MonthlyReportResponse getMonthlyReport(
            int year,
            int month,
            Long userId);

    YearlyReportResponse getYearlyReport(
            int year,
            Long userId);
}