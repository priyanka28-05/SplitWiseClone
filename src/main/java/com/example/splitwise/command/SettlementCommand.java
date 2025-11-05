package com.example.splitwise.command;

import com.example.splitwise.model.request.SettlementRequestModel;
import lombok.*;

/**
 * Unified command class for all Settlement operations.
 * <p>
 * Supports:
 * - Settling a payment
 * - Fetching settlements by user
 * - Fetching settlements by group
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettlementCommand {

    /**
     * Defines the type of settlement operation to perform.
     */
    public enum CommandType {
        SETTLE_PAYMENT,
        GET_SETTLEMENTS_BY_USER,
        GET_SETTLEMENTS_BY_GROUP
    }

    /**
     * The type of command to execute.
     */
    private CommandType commandType;

    /**
     * The settlement request model (used for SETTLE_PAYMENT).
     */
    private SettlementRequestModel settlementRequest;

    /**
     * Username for GET_SETTLEMENTS_BY_USER command.
     */
    private String userName;

    /**
     * Group name for GET_SETTLEMENTS_BY_GROUP command.
     */
    private String groupName;
}
