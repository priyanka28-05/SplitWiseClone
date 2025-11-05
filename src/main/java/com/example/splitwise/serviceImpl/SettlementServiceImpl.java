package com.example.splitwise.serviceImpl;

import com.example.splitwise.entity.*;
import com.example.splitwise.exception.ResourceNotFoundException;
import com.example.splitwise.model.request.SettlementRequestModel;
import com.example.splitwise.model.response.SettlementResponseModel;
import com.example.splitwise.repository.*;
import com.example.splitwise.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of SettlementService.
 * Focuses purely on business logic — command dispatching happens in the handler.
 */
@Service
@RequiredArgsConstructor
public class SettlementServiceImpl implements SettlementService {

    private final SettlementRepository settlementRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseShareRepository expenseShareRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    /**
     * Handles settling a payment between payer and receiver.
     */
    @Override
    @Transactional
    public SettlementResponseModel settlePayment(SettlementRequestModel request) {

        GroupEntity group = groupRepository.findByGroupName(request.getGroupName())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Group '" + request.getGroupName() + "' not found"));

        UserEntity payer = userRepository.findByUserName(request.getPayerName())
                .orElseThrow(() -> new ResourceNotFoundException("Payer not found"));

        UserEntity receiver = userRepository.findByUserName(request.getReceiverName())
                .orElseThrow(() -> new ResourceNotFoundException("Receiver not found"));

        // Determine which expense to settle
        ExpenseEntity expense = (request.getExpenseDescription() != null && !request.getExpenseDescription().isEmpty())
                ? expenseRepository.findByGroupAndDescription(group, request.getExpenseDescription())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Expense '" + request.getExpenseDescription() + "' not found in group '" + group.getGroupName() + "'"))
                : expenseRepository.findTopByGroupOrderByCreatedAtDesc(group)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No expenses found in group '" + group.getGroupName() + "'"));

        if (request.getAmount() <= 0) {
            throw new IllegalArgumentException("Settlement amount must be greater than zero");
        }

        // Adjust payer’s share
        ExpenseShareEntity payerShare = expenseShareRepository.findByExpenseAndUser(expense, payer)
                .orElseThrow(() -> new ResourceNotFoundException("Payer does not have a share in this expense"));

        if (payerShare.getShareAmount() < request.getAmount()) {
            throw new IllegalArgumentException("Payer does not have enough balance to settle this amount");
        }

        payerShare.setShareAmount(payerShare.getShareAmount() - request.getAmount());
        payerShare.setSettled(payerShare.getShareAmount() == 0);
        expenseShareRepository.save(payerShare);

        // Adjust receiver’s share
        ExpenseShareEntity receiverShare = expenseShareRepository.findByExpenseAndUser(expense, receiver)
                .orElseThrow(() -> new ResourceNotFoundException("Receiver does not have a share in this expense"));

        receiverShare.setShareAmount(receiverShare.getShareAmount() + request.getAmount());
        expenseShareRepository.save(receiverShare);

        // Save settlement record
        SettlementEntity savedSettlement = settlementRepository.save(
                SettlementEntity.builder()
                        .group(group)
                        .payer(payer)
                        .receiver(receiver)
                        .amount(request.getAmount())
                        .settledAt(LocalDateTime.now())
                        .build()
        );

        return SettlementResponseModel.builder()
                .settlementId(savedSettlement.getSettlementId())
                .payerUserName(savedSettlement.getPayer().getUserName())
                .receiverUserName(savedSettlement.getReceiver().getUserName())
                .amount(savedSettlement.getAmount())
                .settledAt(savedSettlement.getSettledAt())
                .message("Settlement successful")
                .build();
    }

    /**
     * Retrieves all settlements involving a specific user.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SettlementResponseModel> getSettlementsByUser(String userName) {

        UserEntity user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return settlementRepository.findByPayerOrReceiver(user, user).stream()
                .map(s -> SettlementResponseModel.builder()
                        .settlementId(s.getSettlementId())
                        .payerUserName(s.getPayer().getUserName())
                        .receiverUserName(s.getReceiver().getUserName())
                        .amount(s.getAmount())
                        .settledAt(s.getSettledAt())
                        .message("Settlement retrieved successfully")
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all settlements for a specific group.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SettlementResponseModel> getSettlementsByGroup(String groupName) {

        groupRepository.findByGroupName(groupName)
                .orElseThrow(() -> new ResourceNotFoundException("Group '" + groupName + "' not found"));

        return settlementRepository.findByGroup_GroupName(groupName).stream()
                .map(s -> SettlementResponseModel.builder()
                        .settlementId(s.getSettlementId())
                        .payerUserName(s.getPayer().getUserName())
                        .receiverUserName(s.getReceiver().getUserName())
                        .amount(s.getAmount())
                        .settledAt(s.getSettledAt())
                        .message("Settlement retrieved successfully")
                        .build())
                .collect(Collectors.toList());
    }
}
