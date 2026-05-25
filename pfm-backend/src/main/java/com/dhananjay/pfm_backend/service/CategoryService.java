package com.dhananjay.pfm_backend.service;

import com.dhananjay.pfm_backend.dto.request.CategoryRequest;
import com.dhananjay.pfm_backend.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(
            CategoryRequest request,
            Long userId);

    List<CategoryResponse> getAllCategories(
            Long userId);
}