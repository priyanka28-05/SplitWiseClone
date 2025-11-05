package com.example.splitwise.model.request;

import lombok.*;

import java.util.List;

/**
 * Represents a request payload for creating a new expense in a group.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseRequestModel {

    /**
     * The name of the group where this expense belongs.
     */
    private String groupName;

    /**
     * The username of the person who created or paid for this expense.
     */
    private String createdByUser;

    /**
     * Description of the expense (e.g., "Dinner", "Cab Ride").
     */
    private String description;

    /**
     * The total expense amount.
     */
    private double totalAmount;

    /**
     * The list of usernames who participated in this expense.
     */
    private List<String> participants;
}
