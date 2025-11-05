package com.example.splitwise.repository;

import com.example.splitwise.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on {@link GroupEntity}.
 * Used for managing groups and retrieving them by name.
 */
@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    /**
     * Finds a group by its unique name.
     *
     * @param groupName the name of the group
     * @return optional containing the group if found
     */
    Optional<GroupEntity> findByGroupName(String groupName);
}
