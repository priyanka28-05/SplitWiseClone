package com.example.splitwise.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Represents an individual user's share in a specific expense.
 * Each record maps one user to one expense, defining how much that user owes or contributed.
 */
@Entity
@Table(name = "expense_shares")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseShareEntity {

    /**
     * Primary key for the ExpenseShareEntity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseShareId;

    /**
     * The expense to which this share belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_id", nullable = false)
    private ExpenseEntity expense;

    /**
     * The user who is responsible for this share of the expense.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    /**
     * The amount owed or paid by this user for this expense.
     */
    @Column(nullable = false)
    private double shareAmount;

    /**
     * Marks whether this user’s share has been fully settled.
     */
    @Column(nullable = false)
    @Builder.Default
    private boolean settled = false;
}
