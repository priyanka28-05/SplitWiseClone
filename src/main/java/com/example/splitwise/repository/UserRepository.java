package com.example.splitwise.repository;

import com.example.splitwise.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for managing {@link UserEntity} persistence operations.
 * Provides lookup by username and email for authentication and user management.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Finds a user by their unique username.
     *
     * @param userName username of the user
     * @return optional containing the user if found
     */
    Optional<UserEntity> findByUserName(String userName);

    /**
     * Finds a user by their email address.
     *
     * @param email email of the user
     * @return optional containing the user if found
     */
    Optional<UserEntity> findByEmail(String email);
}
