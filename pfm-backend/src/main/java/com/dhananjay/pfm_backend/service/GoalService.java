package com.dhananjay.pfm_backend.service;

import com.dhananjay.pfm_backend.dto.request.GoalRequest;
import com.dhananjay.pfm_backend.dto.response.GoalResponse;
import com.dhananjay.pfm_backend.dto.request.UpdateGoalRequest;
import com.dhananjay.pfm_backend.dto.response.MessageResponse;

import java.util.List;

public interface GoalService {

    GoalResponse createGoal(
            GoalRequest request,
            Long userId);

    List<GoalResponse> getAllGoals(
            Long userId);

    GoalResponse getGoalById(
            Long goalId,
            Long userId);

    GoalResponse updateGoal(
            Long goalId,
            UpdateGoalRequest request,
            Long userId);

    MessageResponse deleteGoal(
            Long goalId,
            Long userId);
}