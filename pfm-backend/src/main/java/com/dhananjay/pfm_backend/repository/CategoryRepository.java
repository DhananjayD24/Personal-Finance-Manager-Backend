package com.dhananjay.pfm_backend.repository;

import com.dhananjay.pfm_backend.entity.Category;
import com.dhananjay.pfm_backend.entity.User;
import com.dhananjay.pfm_backend.enums.TransactionType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUser(User user);

    List<Category> findByUserOrUserIsNull(User user);

    Optional<Category> findByNameAndUser(String name, User user);

    boolean existsByNameAndUser(String name, User user);

    List<Category> findByType(TransactionType type);
}