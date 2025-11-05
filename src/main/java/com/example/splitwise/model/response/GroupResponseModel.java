package com.example.splitwise.model.response;

import lombok.*;

import java.util.List;

/**
 * Represents group information returned to the API client.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupResponseModel {

    private Long groupId;
    private String groupName;
    private int memberCount;
    private List<String> memberNames;
    private String message;
}
