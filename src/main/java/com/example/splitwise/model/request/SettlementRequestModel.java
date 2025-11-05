package com.example.splitwise.model.request;

import lombok.*;

/**
 * Request payload for settling an outstanding payment between two users.
 * Does NOT expose any internal IDs.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettlementRequestModel {

    /**
     * The group name where the settlement belongs.
     */
    private String groupName;

    /**
     * The username of the payer.
     */
    private String payerName;

    /**
     * The username of the receiver.
     */
    private String receiverName;

    /**
     * Optional: description of the expense to settle.
     * Can be used to identify the exact expense internally.
     */
    private String expenseDescription;

    /**
     * The amount paid for settlement.
     */
    private double amount;
}
