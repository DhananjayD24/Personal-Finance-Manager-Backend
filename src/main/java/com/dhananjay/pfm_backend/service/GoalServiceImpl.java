package com.dhananjay.pfm_backend.service;

import com.dhananjay.pfm_backend.dto.request.GoalRequest;
import com.dhananjay.pfm_backend.dto.response.GoalResponse;

import com.dhananjay.pfm_backend.entity.Goal;
import com.dhananjay.pfm_backend.entity.Transaction;
import com.dhananjay.pfm_backend.entity.User;

import com.dhananjay.pfm_backend.enums.TransactionType;

import com.dhananjay.pfm_backend.exception.ResourceNotFoundException;

import com.dhananjay.pfm_backend.repository.GoalRepository;
import com.dhananjay.pfm_backend.repository.TransactionRepository;
import com.dhananjay.pfm_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.dhananjay.pfm_backend.dto.request.UpdateGoalRequest;
import com.dhananjay.pfm_backend.dto.response.MessageResponse;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl
        implements GoalService {

    private final GoalRepository goalRepository;

    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;

    @Override
    public GoalResponse createGoal(
            GoalRequest request,
            Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found"));

        Goal goal = Goal.builder()
                .goalName(request.getGoalName())
                .targetAmount(request.getTargetAmount())
                .targetDate(request.getTargetDate())
                .startDate(LocalDate.now())
                .user(user)
                .build();

        Goal savedGoal = goalRepository.save(goal);

        return buildGoalResponse(savedGoal);
    }

    @Override
    public List<GoalResponse> getAllGoals(
            Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found"));

        List<Goal> goals = goalRepository.findByUser(user);

        return goals.stream()
                .map(this::buildGoalResponse)
                .toList();
    }

    @Override
    public GoalResponse getGoalById(
            Long goalId,
            Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found"));

        Goal goal = goalRepository.findByIdAndUser(
                goalId,
                user)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Goal not found"));

        return buildGoalResponse(goal);
    }

    @Override
    public GoalResponse updateGoal(
            Long goalId,
            UpdateGoalRequest request,
            Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found"));

        Goal goal = goalRepository.findByIdAndUser(
                goalId,
                user)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Goal not found"));

        goal.setTargetAmount(
                request.getTargetAmount());

        goal.setTargetDate(
                request.getTargetDate());

        Goal updatedGoal = goalRepository.save(goal);

        return buildGoalResponse(updatedGoal);
    }

    @Override
    public MessageResponse deleteGoal(
            Long goalId,
            Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found"));

        Goal goal = goalRepository.findByIdAndUser(
                goalId,
                user)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Goal not found"));

        goalRepository.delete(goal);

        return MessageResponse.builder()
                .message("Goal deleted successfully")
                .build();
    }

    // =========================
    // GOAL CALCULATION LOGIC
    // =========================

    private GoalResponse buildGoalResponse(
            Goal goal) {

        List<Transaction> transactions = transactionRepository
                .findByUserAndDateGreaterThanEqual(
                        goal.getUser(),
                        goal.getStartDate());

        BigDecimal totalIncome = BigDecimal.ZERO;

        BigDecimal totalExpense = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {

            if (transaction.getCategory()
                    .getType() == TransactionType.INCOME) {

                totalIncome = totalIncome.add(
                        transaction.getAmount());

            } else {

                totalExpense = totalExpense.add(
                        transaction.getAmount());
            }
        }

        BigDecimal currentProgress = totalIncome.subtract(totalExpense);

        if (currentProgress.compareTo(BigDecimal.ZERO) < 0) {

            currentProgress = BigDecimal.ZERO;
        }

        BigDecimal remainingAmount = goal.getTargetAmount()
                .subtract(currentProgress);

        if (remainingAmount.compareTo(BigDecimal.ZERO) < 0) {

            remainingAmount = BigDecimal.ZERO;
        }

        double percentage = currentProgress
                .divide(
                        goal.getTargetAmount(),
                        4,
                        java.math.RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .doubleValue();

        return GoalResponse.builder()
                .id(goal.getId())
                .goalName(goal.getGoalName())
                .targetAmount(goal.getTargetAmount())
                .targetDate(goal.getTargetDate())
                .startDate(goal.getStartDate())
                .currentProgress(currentProgress)
                .progressPercentage(percentage)
                .remainingAmount(remainingAmount)
                .build();
    }
}