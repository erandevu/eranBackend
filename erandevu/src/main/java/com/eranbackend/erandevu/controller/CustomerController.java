package com.eranbackend.erandevu.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eranbackend.erandevu.dto.CustomerDto;
import com.eranbackend.erandevu.service.CustomerService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

  
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.saveCustomer(customerDto);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.updateCustomer(customerDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getCustomerById(@PathVariable UUID id) {
        return customerService.getCustomerById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteCustomer(@PathVariable UUID id) {
        return customerService.deleteCustomer(id);
    }

}
