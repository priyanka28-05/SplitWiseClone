package com.example.splitwise.controller;

import com.example.splitwise.command.ExpenseCommand;
import com.example.splitwise.commandHandler.ExpenseCommandHandler;
import com.example.splitwise.model.request.ExpenseRequestModel;
import com.example.splitwise.model.response.ExpenseResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing expenses.
 * Uses Command + CommandHandler pattern for clean separation.
 */
@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseCommandHandler expenseCommandHandler;

    /**
     * Add a new expense and split among participants.
     */
    @PostMapping
    public ResponseEntity<ExpenseResponseModel> addExpense(@RequestBody ExpenseRequestModel request) {
        ExpenseCommand command = ExpenseCommand.builder()
                .commandType(ExpenseCommand.CommandType.ADD_EXPENSE)
                .expenseRequest(request)
                .build();

        ExpenseResponseModel response = (ExpenseResponseModel) expenseCommandHandler.handle(command);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all expenses for a specific group.
     */
    @GetMapping("/group/{groupName}")
    public ResponseEntity<List<ExpenseResponseModel>> getExpensesByGroup(@PathVariable String groupName) {
        ExpenseCommand command = ExpenseCommand.builder()
                .commandType(ExpenseCommand.CommandType.GET_EXPENSES_BY_GROUP)
                .groupName(groupName)
                .build();

        @SuppressWarnings("unchecked")
        List<ExpenseResponseModel> response = (List<ExpenseResponseModel>) expenseCommandHandler.handle(command);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all expenses for a specific user.
     */
    @GetMapping("/user/{userName}")
    public ResponseEntity<List<ExpenseResponseModel>> getExpensesByUser(@PathVariable String userName) {
        ExpenseCommand command = ExpenseCommand.builder()
                .commandType(ExpenseCommand.CommandType.GET_EXPENSES_BY_USER)
                .userName(userName)
                .build();

        @SuppressWarnings("unchecked")
        List<ExpenseResponseModel> response = (List<ExpenseResponseModel>) expenseCommandHandler.handle(command);
        return ResponseEntity.ok(response);
    }
}
