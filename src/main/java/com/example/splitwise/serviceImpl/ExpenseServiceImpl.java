package com.example.splitwise.serviceImpl;

import com.example.splitwise.entity.ExpenseEntity;
import com.example.splitwise.entity.ExpenseShareEntity;
import com.example.splitwise.entity.GroupEntity;
import com.example.splitwise.entity.UserEntity;
import com.example.splitwise.exception.ResourceNotFoundException;
import com.example.splitwise.model.request.ExpenseRequestModel;
import com.example.splitwise.model.response.ExpenseResponseModel;
import com.example.splitwise.repository.ExpenseRepository;
import com.example.splitwise.repository.ExpenseShareRepository;
import com.example.splitwise.repository.GroupRepository;
import com.example.splitwise.repository.UserRepository;
import com.example.splitwise.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service implementation for managing expenses.
 * Handles expense creation, retrieval by group, and retrieval by user.
 * Business logic resides here — called from CommandHandler.
 */
@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseShareRepository expenseShareRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    /**
     * Add a new expense and split it among participants.
     */
    @Override
    @Transactional
    public ExpenseResponseModel addExpense(ExpenseRequestModel request) {
        // Validate group and creator
        GroupEntity group = groupRepository.findByGroupName(request.getGroupName())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Group '" + request.getGroupName() + "' not found"));

        UserEntity creator = userRepository.findByUserName(request.getCreatedByUser())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User '" + request.getCreatedByUser() + "' not found"));

        // Map participant names to internal UserEntity objects
        Set<UserEntity> participants = request.getParticipants().stream()
                .map(userName -> userRepository.findByUserName(userName)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "User '" + userName + "' not found")))
                .collect(Collectors.toSet());

        // Ensure all participants belong to the group
        for (UserEntity participant : participants) {
            if (!group.getMembers().contains(participant)) {
                throw new ResourceNotFoundException(
                        "User '" + participant.getUserName() + "' is not part of group '" + group.getGroupName() + "'");
            }
        }

        // Create and save expense
        ExpenseEntity expenseEntity = ExpenseEntity.builder()
                .group(group)
                .createdBy(creator)
                .description(request.getDescription())
                .totalAmount(request.getTotalAmount())
                .createdAt(LocalDateTime.now())
                .build();

        ExpenseEntity savedExpense = expenseRepository.save(expenseEntity);

        // Split the expense equally among participants
        double splitAmount = request.getTotalAmount() / participants.size();

        for (UserEntity participant : participants) {
            ExpenseShareEntity share = ExpenseShareEntity.builder()
                    .expense(savedExpense)
                    .user(participant)
                    .shareAmount(splitAmount)
                    .settled(false)
                    .build();
            expenseShareRepository.save(share);
        }

        return buildExpenseResponse(savedExpense, participants, "Expense added successfully");
    }

    /**
     * Retrieve all expenses for a specific group.
     */
    @Override
    public List<ExpenseResponseModel> getExpensesByGroup(String groupName) {
        GroupEntity group = groupRepository.findByGroupName(groupName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Group '" + groupName + "' not found"));

        return expenseRepository.findByGroupWithSharesAndUsers(group).stream()
                .map(expense -> buildExpenseResponse(
                        expense,
                        expense.getExpenseShares().stream()
                                .map(ExpenseShareEntity::getUser)
                                .collect(Collectors.toSet()),
                        "Expense retrieved successfully"))
                .collect(Collectors.toList());
    }

    /**
     * Retrieve all expenses for a specific user.
     */
    @Override
    public List<ExpenseResponseModel> getExpensesByUser(String userName) {
        UserEntity user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User '" + userName + "' not found"));

        return expenseShareRepository.findByUserWithExpenseAndShares(user).stream()
                .map(share -> buildExpenseResponse(
                        share.getExpense(),
                        share.getExpense().getExpenseShares().stream()
                                .map(ExpenseShareEntity::getUser)
                                .collect(Collectors.toSet()),
                        "Expense retrieved successfully"))
                .collect(Collectors.toList());
    }

    /**
     * Utility method to map ExpenseEntity to ExpenseResponseModel.
     */
    private ExpenseResponseModel buildExpenseResponse(ExpenseEntity expense,
                                                      Set<UserEntity> participants,
                                                      String message) {
        return ExpenseResponseModel.builder()
                .expenseId(null) // internal ID hidden
                .groupName(expense.getGroup().getGroupName())
                .createdByUser(expense.getCreatedBy().getUserName())
                .description(expense.getDescription())
                .totalAmount(expense.getTotalAmount())
                .participants(participants.stream()
                        .map(UserEntity::getUserName)
                        .collect(Collectors.toList()))
                .message(message)
                .build();
    }
}
