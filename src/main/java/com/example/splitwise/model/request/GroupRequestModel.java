package com.example.splitwise.model.request;

import lombok.*;
import java.util.List;

/**
 * Represents the payload for creating a new group and adding members.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupRequestModel {

    /**
     * The unique name of the group to create.
     */
    private String groupName;

    /**
     * The list of usernames to be added as members of this group.
     */
    private List<String> memberNames;
}
