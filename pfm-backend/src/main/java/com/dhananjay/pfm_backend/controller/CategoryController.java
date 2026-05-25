package com.dhananjay.pfm_backend.controller;

import com.dhananjay.pfm_backend.dto.request.CategoryRequest;
import com.dhananjay.pfm_backend.dto.response.CategoryResponse;

import com.dhananjay.pfm_backend.exception.UnauthorizedException;

import com.dhananjay.pfm_backend.service.CategoryService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")

@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryRequest request,
            HttpSession session) {

        Long userId =
                (Long) session.getAttribute("userId");

        if (userId == null) {

            throw new UnauthorizedException(
                    "User not logged in");
        }

        CategoryResponse response =
                categoryService.createCategory(
                        request,
                        userId);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>>
    getAllCategories(HttpSession session) {

        Long userId =
                (Long) session.getAttribute("userId");

        if (userId == null) {

            throw new UnauthorizedException(
                    "User not logged in");
        }

        List<CategoryResponse> response =
                categoryService.getAllCategories(
                        userId);

        return ResponseEntity.ok(response);
    }
}