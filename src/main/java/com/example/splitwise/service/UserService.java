package com.example.splitwise.service;

import com.example.splitwise.model.request.UserRequestModel;
import com.example.splitwise.model.response.UserResponseModel;

import java.util.List;

/**
 * Service interface for managing user operations.
 */
public interface UserService {

    /**
     * Registers a new user.
     *
     * @param userRequest the incoming user data
     * @return the created user details
     */
    UserResponseModel registerUser(UserRequestModel userRequest);

    /**
     * Retrieves user details by username.
     *
     * @param userName the username
     * @return user details
     */
    UserResponseModel getUserByName(String userName);

    /**
     * Returns all users in the system.
     *
     * @return list of users
     */
    List<UserResponseModel> getAllUsers();
}
