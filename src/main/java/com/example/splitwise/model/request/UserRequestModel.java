package com.example.splitwise.model.request;

import lombok.*;

/**
 * Represents a request payload for creating or registering a user.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestModel {

    /**
     * Display name of the user.
     */
    private String userName;

    /**
     * Email address used for login or OAuth.
     */
    private String email;

    /**
     * OAuth provider identifier (optional).
     */
    private String oauthId;
}
