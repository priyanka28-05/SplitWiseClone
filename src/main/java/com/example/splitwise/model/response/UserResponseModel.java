package com.example.splitwise.model.response;

import lombok.*;

/**
 * Represents user details returned to clients.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseModel {

    private Long userId;
    private String userName;
    private String email;
    private String message;
}
