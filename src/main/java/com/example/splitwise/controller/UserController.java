package com.example.splitwise.controller;

import com.example.splitwise.command.UserCommand;
import com.example.splitwise.command.UserCommand.CommandType;
import com.example.splitwise.commandHandler.UserCommandHandler;
import com.example.splitwise.model.request.UserRequestModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing users.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserCommandHandler userCommandHandler;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestModel request) {
        UserCommand command = UserCommand.builder()
                .commandType(CommandType.REGISTER_USER)
                .userRequest(request)
                .build();
        return ResponseEntity.ok(userCommandHandler.handle(command));
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> getUserByName(@PathVariable String userName) {
        UserCommand command = UserCommand.builder()
                .commandType(CommandType.GET_USER_BY_NAME)
                .userName(userName)
                .build();
        return ResponseEntity.ok(userCommandHandler.handle(command));
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        UserCommand command = UserCommand.builder()
                .commandType(CommandType.GET_ALL_USERS)
                .build();
        return ResponseEntity.ok(userCommandHandler.handle(command));
    }
}
