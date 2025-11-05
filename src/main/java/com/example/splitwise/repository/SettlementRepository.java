package com.example.splitwise.repository;

import com.example.splitwise.entity.SettlementEntity;
import com.example.splitwise.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettlementRepository extends JpaRepository<SettlementEntity, Long> {

    /**
     * Find settlements where a user is either the payer or receiver.
     */
    List<SettlementEntity> findByPayerOrReceiver(UserEntity payer, UserEntity receiver);

    /**
     * Find all settlements belonging to a group.
     */
    List<SettlementEntity> findByGroup_GroupName(String groupName);
}
