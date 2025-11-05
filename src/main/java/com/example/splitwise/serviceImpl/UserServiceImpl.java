package com.example.splitwise.serviceImpl;

import com.example.splitwise.entity.UserEntity;
import com.example.splitwise.exception.ResourceAlreadyExistsException;
import com.example.splitwise.exception.ResourceNotFoundException;
import com.example.splitwise.model.request.UserRequestModel;
import com.example.splitwise.model.response.UserResponseModel;
import com.example.splitwise.repository.UserRepository;
import com.example.splitwise.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link UserService}.
 * Handles user registration, retrieval, and listing.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseModel registerUser(UserRequestModel userRequest) {
        userRepository.findByEmail(userRequest.getEmail()).ifPresent(u -> {
            throw new ResourceAlreadyExistsException("User with email '" + userRequest.getEmail() + "' already exists");
        });

        UserEntity userEntity = UserEntity.builder()
                .userName(userRequest.getUserName())
                .email(userRequest.getEmail())
                .oauthId(userRequest.getOauthId())
                .build();

        UserEntity savedUser = userRepository.save(userEntity);

        return UserResponseModel.builder()
                .userId(savedUser.getUserId())
                .userName(savedUser.getUserName())
                .email(savedUser.getEmail())
                .message("User registered successfully")
                .build();
    }

    @Override
    public UserResponseModel getUserByName(String userName) {
        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User '" + userName + "' not found"));

        return UserResponseModel.builder()
                .userId(userEntity.getUserId())
                .userName(userEntity.getUserName())
                .email(userEntity.getEmail())
                .message("User retrieved successfully")
                .build();
    }

    @Override
    public List<UserResponseModel> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserResponseModel.builder()
                        .userId(user.getUserId())
                        .userName(user.getUserName())
                        .email(user.getEmail())
                        .message("User retrieved successfully")
                        .build())
                .collect(Collectors.toList());
    }
}
