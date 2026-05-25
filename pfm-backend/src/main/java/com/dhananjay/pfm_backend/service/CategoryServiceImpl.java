package com.dhananjay.pfm_backend.service;

import com.dhananjay.pfm_backend.dto.request.CategoryRequest;
import com.dhananjay.pfm_backend.dto.response.CategoryResponse;

import com.dhananjay.pfm_backend.entity.Category;
import com.dhananjay.pfm_backend.entity.User;

import com.dhananjay.pfm_backend.exception.DuplicateResourceException;
import com.dhananjay.pfm_backend.exception.ResourceNotFoundException;

import com.dhananjay.pfm_backend.repository.CategoryRepository;
import com.dhananjay.pfm_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl
        implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    @Override
    public CategoryResponse createCategory(
            CategoryRequest request,
            Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        boolean exists =
                categoryRepository.existsByNameAndUser(
                        request.getName(),
                        user);

        if (exists) {

            throw new DuplicateResourceException(
                    "Category already exists");
        }

        Category category = Category.builder()
                .name(request.getName())
                .type(request.getType())
                .isCustom(true)
                .user(user)
                .build();

        Category savedCategory =
                categoryRepository.save(category);

        return CategoryResponse.builder()
                .id(savedCategory.getId())
                .name(savedCategory.getName())
                .type(savedCategory.getType())
                .isCustom(savedCategory.isCustom())
                .build();
    }

    @Override
    public List<CategoryResponse> getAllCategories(
            Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        List<Category> categories =
                categoryRepository
                        .findByUserOrUserIsNull(user);

        return categories.stream()
                .map(category -> CategoryResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .type(category.getType())
                        .isCustom(category.isCustom())
                        .build())
                .toList();
    }
}