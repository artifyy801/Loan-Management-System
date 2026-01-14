package com.project.loan_system.controller;

import com.project.loan_system.dto.ApiResponse;
import com.project.loan_system.model.Customer;
import com.project.loan_system.repository.CustomerRepository;
import com.project.loan_system.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody Map<String,String> request){
        String email = request.get("email");
        String password = request.get("password");

        Optional<Customer> customer = customerRepository.findByEmail(email);

        if(customer.isPresent() && customer.get().getPassword().equals(password)){
            String token = jwtUtil.generateToken(email);
            return Map.of("token",token,"message", "Login Successful");
        }else{
            throw new RuntimeException("Invalid Credentials");
        }
    }

    @PostMapping("signup")
    public ResponseEntity<?> signUp(@RequestBody Customer customer){
        if (customerRepository.existsByEmail(customer.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Error: Email is already in use!", false));
        }
        customerRepository.save(customer);
        return ResponseEntity.ok(new ApiResponse("User registered successfully!", true));
    }
}
