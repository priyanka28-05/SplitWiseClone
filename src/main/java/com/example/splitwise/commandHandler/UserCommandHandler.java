package com.example.splitwise.commandHandler;

import com.example.splitwise.command.UserCommand;
import com.example.splitwise.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Handles execution of {@link UserCommand}.
 * Delegates processing to {@link UserService}.
 */
@Component
@RequiredArgsConstructor
public class UserCommandHandler {

    private final UserService userService;

    /**
     * Executes the provided user command.
     *
     * @param command the unified user command
     * @return response object or list depending on command type
     */
    public Object handle(UserCommand command) {
        switch (command.getCommandType()) {

            case REGISTER_USER:
                if (command.getUserRequest() == null) {
                    throw new IllegalArgumentException("User request must not be null for REGISTER_USER command");
                }
                return userService.registerUser(command.getUserRequest());

            case GET_USER_BY_NAME:
                if (command.getUserName() == null || command.getUserName().isEmpty()) {
                    throw new IllegalArgumentException("User name must not be empty for GET_USER_BY_NAME command");
                }
                return userService.getUserByName(command.getUserName());

            case GET_ALL_USERS:
                return userService.getAllUsers();

            default:
                throw new UnsupportedOperationException("Unsupported command type: " + command.getCommandType());
        }
    }
}
