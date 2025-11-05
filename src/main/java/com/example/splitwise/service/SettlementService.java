package com.example.splitwise.service;

import com.example.splitwise.model.request.SettlementRequestModel;
import com.example.splitwise.model.response.SettlementResponseModel;

import java.util.List;

/**
 * Service interface for handling settlements.
 */
public interface SettlementService {

    /**
     * Settle a payment between payer and receiver.
     */
    SettlementResponseModel settlePayment(SettlementRequestModel request);

    /**
     * Get all settlements for a specific user.
     */
    List<SettlementResponseModel> getSettlementsByUser(String userName);

    /**
     * Get all settlements for a specific group.
     */
    List<SettlementResponseModel> getSettlementsByGroup(String groupName);
}
