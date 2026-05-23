package com.dhananjay.pfm_backend.repository;

import com.dhananjay.pfm_backend.entity.Goal;
import com.dhananjay.pfm_backend.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    List<Goal> findByUser(User user);
}