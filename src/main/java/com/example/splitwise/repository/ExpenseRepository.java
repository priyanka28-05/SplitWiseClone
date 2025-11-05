package com.example.splitwise.repository;

import com.example.splitwise.entity.ExpenseEntity;
import com.example.splitwise.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    @Query("SELECT e FROM ExpenseEntity e " +
            "LEFT JOIN FETCH e.createdBy " +
            "LEFT JOIN FETCH e.group " +
            "LEFT JOIN FETCH e.expenseShares es " +
            "LEFT JOIN FETCH es.user " +
            "WHERE e.group = :group")
    List<ExpenseEntity> findByGroupWithSharesAndUsers(@Param("group") GroupEntity group);

    Optional<ExpenseEntity> findByGroupAndDescription(GroupEntity group, String description);

    Optional<ExpenseEntity> findTopByGroupOrderByCreatedAtDesc(GroupEntity group);
}
