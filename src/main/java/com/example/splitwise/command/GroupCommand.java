package com.example.splitwise.command;

import com.example.splitwise.model.request.GroupRequestModel;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GroupCommand {

    public enum Action {
        CREATE,
        GET_BY_NAME,
        ADD_MEMBERS,
        REMOVE_MEMBERS,
        GET_ALL
    }

    private Action action;
    private GroupRequestModel request;
    private String groupName;
    private List<String> memberNames;
}
