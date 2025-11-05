package com.example.splitwise.controller;

import com.example.splitwise.command.SettlementCommand;
import com.example.splitwise.command.SettlementCommand.CommandType;
import com.example.splitwise.commandHandler.SettlementCommandHandler;
import com.example.splitwise.model.request.SettlementRequestModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling settlement-related operations.
 *
 * Delegates all logic to {@link SettlementCommandHandler}.
 */
@RestController
@RequestMapping("/api/settlements")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementCommandHandler settlementCommandHandler;

    /**
     * Settle a payment between two users.
     *
     * @param request settlement request payload
     * @return response with settlement details
     */
    @PostMapping("/settle")
    public ResponseEntity<?> settlePayment(@RequestBody SettlementRequestModel request) {
        SettlementCommand command = SettlementCommand.builder()
                .commandType(CommandType.SETTLE_PAYMENT)
                .settlementRequest(request)
                .build();

        return ResponseEntity.ok(settlementCommandHandler.handle(command));
    }

    /**
     * Get all settlements for a specific user.
     *
     * @param userName the username
     * @return list of settlements
     */
    @GetMapping("/user/{userName}")
    public ResponseEntity<?> getSettlementsByUser(@PathVariable String userName) {
        SettlementCommand command = SettlementCommand.builder()
                .commandType(CommandType.GET_SETTLEMENTS_BY_USER)
                .userName(userName)
                .build();

        return ResponseEntity.ok(settlementCommandHandler.handle(command));
    }

    /**
     * Get all settlements for a specific group.
     *
     * @param groupName the group name
     * @return list of settlements
     */
    @GetMapping("/group/{groupName}")
    public ResponseEntity<?> getSettlementsByGroup(@PathVariable String groupName) {
        SettlementCommand command = SettlementCommand.builder()
                .commandType(CommandType.GET_SETTLEMENTS_BY_GROUP)
                .groupName(groupName)
                .build();

        return ResponseEntity.ok(settlementCommandHandler.handle(command));
    }
}
