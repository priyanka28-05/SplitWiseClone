package com.example.splitwise.commandHandler;

import com.example.splitwise.command.ExpenseCommand;
import com.example.splitwise.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Handles execution of {@link ExpenseCommand}.
 *
 * Delegates to {@link ExpenseService} based on {@link ExpenseCommand.CommandType}.
 */
@Service
@RequiredArgsConstructor
public class ExpenseCommandHandler {

    private final ExpenseService expenseService;

    /**
     * Executes a unified expense command and delegates it to the appropriate service call.
     *
     * @param command unified command object containing operation type and data
     * @return the result of the executed command
     */
    public Object handle(ExpenseCommand command) {
        if (command == null || command.getCommandType() == null) {
            throw new IllegalArgumentException("Expense command and command type must not be null");
        }

        switch (command.getCommandType()) {

            case ADD_EXPENSE:
                validateAddExpense(command);
                return expenseService.addExpense(command.getExpenseRequest());

            case GET_EXPENSES_BY_GROUP:
                validateGroupName(command);
                return expenseService.getExpensesByGroup(command.getGroupName());

            case GET_EXPENSES_BY_USER:
                validateUserName(command);
                return expenseService.getExpensesByUser(command.getUserName());

            default:
                throw new UnsupportedOperationException("Unsupported command type: " + command.getCommandType());
        }
    }

    // --- Validation helpers ---

    private void validateAddExpense(ExpenseCommand command) {
        if (command.getExpenseRequest() == null) {
            throw new IllegalArgumentException("Expense request must not be null for ADD_EXPENSE command");
        }
    }

    private void validateGroupName(ExpenseCommand command) {
        if (command.getGroupName() == null || command.getGroupName().isEmpty()) {
            throw new IllegalArgumentException("Group name must not be empty for GET_EXPENSES_BY_GROUP command");
        }
    }

    private void validateUserName(ExpenseCommand command) {
        if (command.getUserName() == null || command.getUserName().isEmpty()) {
            throw new IllegalArgumentException("Username must not be empty for GET_EXPENSES_BY_USER command");
        }
    }
}
