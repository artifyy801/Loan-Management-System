package com.project.loan_system.repository;

import com.project.loan_system.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,String> {
    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);
}
