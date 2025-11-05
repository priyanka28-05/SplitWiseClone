package com.example.splitwise.serviceImpl;

import com.example.splitwise.entity.GroupEntity;
import com.example.splitwise.entity.UserEntity;
import com.example.splitwise.exception.ResourceAlreadyExistsException;
import com.example.splitwise.exception.ResourceNotFoundException;
import com.example.splitwise.model.request.GroupRequestModel;
import com.example.splitwise.model.response.GroupResponseModel;
import com.example.splitwise.repository.GroupRepository;
import com.example.splitwise.repository.UserRepository;
import com.example.splitwise.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles all group-related business logic.
 * Called by GroupCommandHandler in CQRS architecture.
 */
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public GroupResponseModel createGroup(GroupRequestModel request) {
        groupRepository.findByGroupName(request.getGroupName()).ifPresent(g -> {
            throw new ResourceAlreadyExistsException("Group '" + request.getGroupName() + "' already exists");
        });

        Set<UserEntity> members = request.getMemberNames().stream()
                .map(name -> userRepository.findByUserName(name)
                        .orElseThrow(() -> new ResourceNotFoundException("User '" + name + "' not found")))
                .collect(Collectors.toSet());

        GroupEntity savedGroup = groupRepository.save(
                GroupEntity.builder()
                        .groupName(request.getGroupName())
                        .members(members)
                        .build()
        );

        return mapToResponse(savedGroup, "Group created successfully");
    }

    @Override
    public GroupResponseModel getGroupByName(String groupName) {
        GroupEntity groupEntity = getGroupEntity(groupName);
        return mapToResponse(groupEntity, "Group retrieved successfully");
    }

    @Override
    @Transactional
    public GroupResponseModel addMembers(String groupName, List<String> memberNames) {
        GroupEntity groupEntity = getGroupEntity(groupName);

        memberNames.forEach(name -> {
            UserEntity user = userRepository.findByUserName(name)
                    .orElseThrow(() -> new ResourceNotFoundException("User '" + name + "' not found"));
            groupEntity.getMembers().add(user);
        });

        GroupEntity updated = groupRepository.save(groupEntity);
        return mapToResponse(updated, "Members added successfully");
    }

    @Override
    @Transactional
    public GroupResponseModel removeMembers(String groupName, List<String> memberNames) {
        GroupEntity groupEntity = getGroupEntity(groupName);

        memberNames.forEach(name -> {
            UserEntity user = userRepository.findByUserName(name)
                    .orElseThrow(() -> new ResourceNotFoundException("User '" + name + "' not found"));
            groupEntity.getMembers().remove(user);
        });

        GroupEntity updated = groupRepository.save(groupEntity);
        return mapToResponse(updated, "Members removed successfully");
    }

    @Override
    public List<GroupResponseModel> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(group -> mapToResponse(group, "Group retrieved successfully"))
                .collect(Collectors.toList());
    }

    // --- Helper methods ---

    private GroupEntity getGroupEntity(String groupName) {
        return groupRepository.findByGroupName(groupName)
                .orElseThrow(() -> new ResourceNotFoundException("Group '" + groupName + "' not found"));
    }

    private GroupResponseModel mapToResponse(GroupEntity group, String message) {
        return GroupResponseModel.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .memberCount(group.getMembers().size())
                .memberNames(group.getMembers().stream()
                        .map(UserEntity::getUserName)
                        .collect(Collectors.toList()))
                .message(message)
                .build();
    }
}
