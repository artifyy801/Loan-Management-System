package com.project.loan_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreditRequest {
    private double salary;
    private double amount;
}
