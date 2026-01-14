package com.project.loan_system.controller;

import com.project.loan_system.model.Customer;
import com.project.loan_system.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    @PostMapping
    public Customer registerCustomer(@RequestBody Customer customer){
        return customerRepository.save(customer);
    }
}
