package com.example.splitwise.commandHandler;

import com.example.splitwise.command.SettlementCommand;
import com.example.splitwise.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Handles execution of {@link SettlementCommand}.
 * <p>
 * Delegates business logic execution to {@link SettlementService}.
 */
@Component
@RequiredArgsConstructor
public class SettlementCommandHandler {

    private final SettlementService settlementService;

    /**
     * Executes the given settlement command.
     *
     * @param command the command to execute
     * @return response object (SettlementResponseModel or List)
     */
    public Object handle(SettlementCommand command) {
        switch (command.getCommandType()) {

            case SETTLE_PAYMENT:
                if (command.getSettlementRequest() == null) {
                    throw new IllegalArgumentException("Settlement request must not be null for SETTLE_PAYMENT command");
                }
                return settlementService.settlePayment(command.getSettlementRequest());

            case GET_SETTLEMENTS_BY_USER:
                if (command.getUserName() == null || command.getUserName().isEmpty()) {
                    throw new IllegalArgumentException("Username must not be empty for GET_SETTLEMENTS_BY_USER command");
                }
                return settlementService.getSettlementsByUser(command.getUserName());

            case GET_SETTLEMENTS_BY_GROUP:
                if (command.getGroupName() == null || command.getGroupName().isEmpty()) {
                    throw new IllegalArgumentException("Group name must not be empty for GET_SETTLEMENTS_BY_GROUP command");
                }
                return settlementService.getSettlementsByGroup(command.getGroupName());

            default:
                throw new UnsupportedOperationException("Unsupported command type: " + command.getCommandType());
        }
    }
}
