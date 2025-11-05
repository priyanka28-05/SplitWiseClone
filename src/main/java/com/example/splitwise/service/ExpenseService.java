package com.example.splitwise.service;

import com.example.splitwise.model.request.ExpenseRequestModel;
import com.example.splitwise.model.response.ExpenseResponseModel;

import java.util.List;

/**
 * Service interface for managing expenses.
 */
public interface ExpenseService {

    /**
     * Adds a new expense in a group, splitting among participants.
     *
     * @param request the expense request
     * @return the created expense response
     */
    ExpenseResponseModel addExpense(ExpenseRequestModel request);

    /**
     * Retrieves all expenses for a specific group.
     *
     * @param groupName group name
     * @return list of expenses
     */
    List<ExpenseResponseModel> getExpensesByGroup(String groupName);

    /**
     * Retrieves all expenses involving a specific user.
     *
     * @param userName user name
     * @return list of expenses
     *
     */

    List<ExpenseResponseModel> getExpensesByUser(String userName);
}