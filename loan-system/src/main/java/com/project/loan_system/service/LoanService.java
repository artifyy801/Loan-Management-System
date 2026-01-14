package com.project.loan_system.service;

import com.project.loan_system.model.Customer;
import com.project.loan_system.model.Loan;
import com.project.loan_system.repository.CustomerRepository;
import com.project.loan_system.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Loan applyForLoan(Loan loan){
        Long customerId = loan.getCustomer().getId();

        Optional<Customer> customerOp = customerRepository.findById(String.valueOf(customerId));
        if(customerOp.isEmpty()){
            throw new RuntimeException("Customer Not found");
        }
        Customer realCustomer = customerOp.get();

        Map<String,Object> requestToPython = new HashMap<>();
        requestToPython.put("salary",realCustomer.getSalary());
        requestToPython.put("amount",loan.getAmount());

        String pythonUrl = "http://localhost:5000/predict";

        Map<String, Object> responseFromModel = restTemplate.postForObject(
          pythonUrl,
          requestToPython,
          Map.class
        );

        assert responseFromModel != null;
        String status = (String) responseFromModel.get("status");

        loan.setStatus(status);

        loan.setReason("ML Confidence: " + responseFromModel.get("confidence") + "%");
        return loanRepository.save(loan);
    }
}
