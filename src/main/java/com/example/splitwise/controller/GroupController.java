package com.example.splitwise.controller;

import com.example.splitwise.exception.ResourceAlreadyExistsException;
import com.example.splitwise.exception.ResourceNotFoundException;
import com.example.splitwise.model.request.GroupRequestModel;
import com.example.splitwise.model.response.GroupResponseModel;
import com.example.splitwise.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing groups.
 * Supports creation, retrieval, member addition/removal, and listing.
 */
@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    /**
     * Creates a new group.
     */
    @PostMapping
    public ResponseEntity<GroupResponseModel> createGroup(@RequestBody GroupRequestModel request) {
        try {
            GroupResponseModel response = groupService.createGroup(request);
            return ResponseEntity.ok(response);
        } catch (ResourceAlreadyExistsException | ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(
                    GroupResponseModel.builder()
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    /**
     * Get group by name.
     */
    @GetMapping("/{groupName}")
    public ResponseEntity<GroupResponseModel> getGroupByName(@PathVariable String groupName) {
        try {
            GroupResponseModel response = groupService.getGroupByName(groupName);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(
                    GroupResponseModel.builder()
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    /**
     * Add members to existing group.
     */
    @PutMapping("/{groupName}/add-members")
    public ResponseEntity<GroupResponseModel> addMembers(
            @PathVariable String groupName,
            @RequestBody List<String> memberNames) {
        try {
            GroupResponseModel response = groupService.addMembers(groupName, memberNames);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(
                    GroupResponseModel.builder()
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    /**
     * Remove members from group.
     */
    @PutMapping("/{groupName}/remove-members")
    public ResponseEntity<GroupResponseModel> removeMembers(
            @PathVariable String groupName,
            @RequestBody List<String> memberNames) {
        try {
            GroupResponseModel response = groupService.removeMembers(groupName, memberNames);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(
                    GroupResponseModel.builder()
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    /**
     * Return all groups.
     */
    @GetMapping
    public ResponseEntity<List<GroupResponseModel>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }
}
