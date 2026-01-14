package com.project.loan_system.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity //Tells Hibernate, this class represents a db table
@Data //tells lombok, generates getters and setters and toString()
@Table(name = "customers") //name the table in MySQL
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private Double salary;
}
