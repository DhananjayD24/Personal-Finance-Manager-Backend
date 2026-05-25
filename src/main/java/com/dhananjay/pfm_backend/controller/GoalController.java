package com.dhananjay.pfm_backend.controller;

import com.dhananjay.pfm_backend.dto.request.GoalRequest;
import com.dhananjay.pfm_backend.dto.response.GoalResponse;

import com.dhananjay.pfm_backend.exception.UnauthorizedException;

import com.dhananjay.pfm_backend.service.GoalService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.dhananjay.pfm_backend.dto.request.UpdateGoalRequest;
import com.dhananjay.pfm_backend.dto.response.MessageResponse;

@RestController
@RequestMapping("/api/goals")

@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @PostMapping
    public ResponseEntity<GoalResponse> createGoal(

            @Valid @RequestBody GoalRequest request,

            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {

            throw new UnauthorizedException(
                    "User not logged in");
        }

        GoalResponse response = goalService.createGoal(
                request,
                userId);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GoalResponse>> getAllGoals(HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {

            throw new UnauthorizedException(
                    "User not logged in");
        }

        List<GoalResponse> response = goalService.getAllGoals(userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalResponse> getGoalById(

            @PathVariable Long id,
            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {

            throw new UnauthorizedException(
                    "User not logged in");
        }

        GoalResponse response = goalService.getGoalById(
                id,
                userId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalResponse> updateGoal(

            @PathVariable Long id,

            @Valid @RequestBody UpdateGoalRequest request,

            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {

            throw new UnauthorizedException(
                    "User not logged in");
        }

        GoalResponse response = goalService.updateGoal(
                id,
                request,
                userId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteGoal(

            @PathVariable Long id,
            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {

            throw new UnauthorizedException(
                    "User not logged in");
        }

        MessageResponse response = goalService.deleteGoal(
                id,
                userId);

        return ResponseEntity.ok(response);
    }
}