package com.project.loan_system.repository;

import com.project.loan_system.model.Customer;
import com.project.loan_system.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Optional;

//JPARepo automatically writes SQL to save,delete, and find loans
public interface LoanRepository extends JpaRepository<Loan, String> {
    List<Loan> findByCustomer(Customer customer);
}
