package com.example.splitwise.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "settlements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettlementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long settlementId;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private UserEntity payer;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UserEntity receiver;

    private double amount;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    private LocalDateTime settledAt;

    @ManyToOne
    @JoinColumn(name = "expense_id")
    private ExpenseEntity expense;
}
