package com.project.loan_system.dto;

import lombok.Data;

@Data
public class CreditResponse {
    private String status;      // "Approved" or "Rejected"
    private Double confidence;  // e.g., 85.5
    private String message;
}