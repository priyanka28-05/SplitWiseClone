package com.example.splitwise.model.response;

import lombok.*;
import java.util.List;

/**
 * Represents details of an expense as returned to the client.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseResponseModel {

    private Long expenseId;
    private String groupName;
    private String description;
    private double totalAmount;
    private String createdByUser;
    private List<String> participants;
    private String message;
}
