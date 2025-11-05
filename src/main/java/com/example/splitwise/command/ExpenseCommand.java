package com.example.splitwise.command;

import com.example.splitwise.model.request.ExpenseRequestModel;
import lombok.*;

/**
 * Unified command object for all Expense operations.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ExpenseCommand {

    /**
     * Defines the type of expense operation to perform.
     */
    public enum CommandType {
        ADD_EXPENSE,
        GET_EXPENSES_BY_GROUP,
        GET_EXPENSES_BY_USER
    }

    private CommandType commandType;
    private ExpenseRequestModel expenseRequest;
    private String groupName;
    private String userName;
}
