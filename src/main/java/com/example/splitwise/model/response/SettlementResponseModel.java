package com.example.splitwise.model.response;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Response model for settlements.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettlementResponseModel {

    private Long settlementId;
    private String payerUserName;
    private String receiverUserName;
    private double amount;
    private LocalDateTime settledAt;
    private String message;
}
