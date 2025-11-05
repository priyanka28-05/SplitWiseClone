package com.example.splitwise.service;

import com.example.splitwise.model.request.GroupRequestModel;
import com.example.splitwise.model.response.GroupResponseModel;

import java.util.List;

/**
 * Service interface for managing groups.
 */
public interface GroupService {

    /**
     * Creates a new group with given members.
     *
     * @param request group creation request
     * @return group details
     */
    GroupResponseModel createGroup(GroupRequestModel request);

    /**
     * Retrieves a group by its name.
     *
     * @param groupName group name
     * @return group details
     */
    GroupResponseModel getGroupByName(String groupName);

    /**
     * Adds members to an existing group.
     *
     * @param groupName   group name
     * @param memberNames list of member usernames to add
     * @return updated group details
     */
    GroupResponseModel addMembers(String groupName, List<String> memberNames);

    /**
     * Removes members from an existing group.
     *
     * @param groupName   group name
     * @param memberNames list of member usernames to remove
     * @return updated group details
     */
    GroupResponseModel removeMembers(String groupName, List<String> memberNames);

    List<GroupResponseModel> getAllGroups();
}
