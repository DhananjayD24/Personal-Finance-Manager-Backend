package com.dhananjay.pfm_backend.config;

import com.dhananjay.pfm_backend.entity.Category;

import com.dhananjay.pfm_backend.enums.TransactionType;

import com.dhananjay.pfm_backend.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {

        createDefaultCategory(
                "Salary",
                TransactionType.INCOME);

        createDefaultCategory(
                "Food",
                TransactionType.EXPENSE);

        createDefaultCategory(
                "Rent",
                TransactionType.EXPENSE);

        createDefaultCategory(
                "Transportation",
                TransactionType.EXPENSE);

        createDefaultCategory(
                "Entertainment",
                TransactionType.EXPENSE);

        createDefaultCategory(
                "Healthcare",
                TransactionType.EXPENSE);

        createDefaultCategory(
                "Utilities",
                TransactionType.EXPENSE);
    }

    private void createDefaultCategory(
            String name,
            TransactionType type) {

        boolean exists =
                categoryRepository.findByNameAndUser(
                        name,
                        null)
                .isPresent();

        if (!exists) {

            Category category = Category.builder()
                    .name(name)
                    .type(type)
                    .isCustom(false)
                    .user(null)
                    .build();

            categoryRepository.save(category);
        }
    }
}