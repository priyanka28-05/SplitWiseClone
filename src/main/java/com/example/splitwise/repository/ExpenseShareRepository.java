package com.example.splitwise.repository;

import com.example.splitwise.entity.ExpenseEntity;
import com.example.splitwise.entity.ExpenseShareEntity;
import com.example.splitwise.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExpenseShareRepository extends JpaRepository<ExpenseShareEntity, Long> {

    @Query("SELECT es FROM ExpenseShareEntity es " +
            "JOIN FETCH es.expense e " +
            "JOIN FETCH e.createdBy " +
            "JOIN FETCH e.group " +
            "JOIN FETCH e.expenseShares es2 " +
            "JOIN FETCH es2.user " +
            "WHERE es.user = :user")
    List<ExpenseShareEntity> findByUserWithExpenseAndShares(@Param("user") UserEntity user);

    Optional<ExpenseShareEntity> findByExpenseAndUser(ExpenseEntity expense, UserEntity user);
}
