package com.project.loan_system.bootstrap;

import com.project.loan_system.model.Customer;
import com.project.loan_system.model.Loan;
import com.project.loan_system.repository.CustomerRepository;
import com.project.loan_system.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanRepository loanRepository;

    public void run(String[] args) throws Exception{
        if (customerRepository.count() == 0) {
            System.out.println("Generating 100 records for customers and loans");
            Random random = new Random();

            for (int i = 1; i <= 100; i++) {
                Customer customer = new Customer();
                customer.setName("Customer" + i);
                customer.setEmail("customer" + i + "@gmail.com");

                double salary = 20000 + (100000 * random.nextDouble());
                customer.setSalary((double) Math.round(salary));

                customerRepository.save(customer);

                Loan l = new Loan();
                l.setStatus("Pending");
                l.setReason("Personal Loan" + i);
                l.setInterestRate(6.72);
                l.setCustomer(customer);

                double amount = 2000 + (1000 * random.nextDouble());
                l.setAmount((double) Math.round(amount));

                loanRepository.save(l);
            }
            System.out.println("Successfully loaded 100 records");
        }else{
            System.out.println("Database already contains data. Skipping initialization.");
        }
    }
}