package com.example.splitwise.command;

import com.example.splitwise.model.request.UserRequestModel;
import lombok.*;

/**
 * Unified command for all user-related operations.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCommand {

    /**
     * Defines the type of user operation to perform.
     */
    public enum CommandType {
        REGISTER_USER,
        GET_USER_BY_NAME,
        GET_ALL_USERS
    }

    /**
     * Type of command to execute.
     */
    private CommandType commandType;

    /**
     * Request body for user registration.
     */
    private UserRequestModel userRequest;

    /**
     * Username for fetching specific user.
     */
    private String userName;
}
