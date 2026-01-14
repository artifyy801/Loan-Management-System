package com.project.loan_system.controller;

import com.project.loan_system.model.Customer;
import com.project.loan_system.model.Loan;
import com.project.loan_system.repository.CustomerRepository;
import com.project.loan_system.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //I am waiter, I handle web requests
@RequestMapping("/loans")
@CrossOrigin("*") //Allow Angular to access this
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/apply")
    public Loan applyForLoan(@RequestBody Loan loan, @RequestParam String email){
        Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Customer Not Found"));
        loan.setCustomer(customer);
        loan.setStatus("PENDING");

        return loanRepository.save(loan);
    }

    @GetMapping("/my-loans")
    public List<Loan> getMyLoans(@RequestParam String email){
        Customer customer = customerRepository.findByEmail(email).orElseThrow(()->new RuntimeException("Customer Not found"));

        return loanRepository.findByCustomer(customer);
    }
}
