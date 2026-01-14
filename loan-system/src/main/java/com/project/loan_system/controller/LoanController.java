package com.project.loan_system.controller;

import com.project.loan_system.dto.CreditRequest;
import com.project.loan_system.dto.CreditResponse;
import com.project.loan_system.model.Customer;
import com.project.loan_system.model.Loan;
import com.project.loan_system.repository.CustomerRepository;
import com.project.loan_system.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController //I am waiter, I handle web requests
@RequestMapping("/loans")
@CrossOrigin("*") //Allow Angular to access this
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/apply")
    public Loan applyForLoan(@RequestBody Loan loan, @RequestParam String email){
        Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Customer Not Found"));
        loan.setCustomer(customer);
        loan.setStatus("PENDING");

        if ("Personal".equalsIgnoreCase(loan.getReason())) {
            loan.setInterestRate(12.0);
        } else {
            loan.setInterestRate(10.0);
        }

        Double salary = (customer.getSalary() != null) ? customer.getSalary() : 0.0;
        CreditRequest request = new CreditRequest(salary, loan.getAmount());

        String pythonUrl = "http://localhost:5001/predict";
        try{
            CreditResponse apiResponse = restTemplate.postForObject(pythonUrl,request,CreditResponse.class);

            if(apiResponse!=null){
                loan.setStatus(apiResponse.getStatus().toUpperCase());
                System.out.println("Decision" + apiResponse.getMessage());
            }else{
                loan.setStatus("PENDING");
            }
        }catch (Exception e){
            System.out.println("Error Message " + e.getMessage());
            loan.setStatus("PENDING");
        }

        return loanRepository.save(loan);
    }

    @GetMapping("/my-loans")
    public List<Loan> getMyLoans(@RequestParam String email){
        Customer customer = customerRepository.findByEmail(email).orElseThrow(()->new RuntimeException("Customer Not found"));

        return loanRepository.findByCustomer(customer);
    }
}
