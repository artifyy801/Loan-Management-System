package com.project.loan_system.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private String reason;

    private String status;

    private Double interestRate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
