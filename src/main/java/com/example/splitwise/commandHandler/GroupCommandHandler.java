package com.example.splitwise.commandHandler;

import com.example.splitwise.command.GroupCommand;
import com.example.splitwise.model.response.GroupResponseModel;
import com.example.splitwise.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupCommandHandler {

    private final GroupService groupService;

    public GroupResponseModel handle(GroupCommand command) {
        switch (command.getAction()) {
            case CREATE:
                return groupService.createGroup(command.getRequest());
            case GET_BY_NAME:
                return groupService.getGroupByName(command.getGroupName());
            case ADD_MEMBERS:
                return groupService.addMembers(command.getGroupName(), command.getMemberNames());
            case REMOVE_MEMBERS:
                return groupService.removeMembers(command.getGroupName(), command.getMemberNames());
            default:
                throw new IllegalArgumentException("Unsupported action: " + command.getAction());
        }
    }

    public List<GroupResponseModel> handleAll(GroupCommand command) {
        if (command.getAction() == GroupCommand.Action.GET_ALL) {
            return groupService.getAllGroups();
        }
        throw new IllegalArgumentException("Unsupported action for list retrieval: " + command.getAction());
    }
}
