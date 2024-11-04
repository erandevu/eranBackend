package com.eranbackend.erandevu.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eranbackend.erandevu.dto.CustomerDto;
import com.eranbackend.erandevu.entity.Customer;
import com.eranbackend.erandevu.repository.CustomerRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CustomerService {

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private CustomerRepository customerRepository;


    public Customer saveCustomer(CustomerDto customerDto) {

        Optional<Customer> findCustomer = customerRepository.findById(customerDto.getId());

        if (customerDto != null && findCustomer.isEmpty()) {
            Customer newCustomer = new Customer();
            newCustomer = modelMapper.map(customerDto, Customer.class);
            newCustomer.setStatus(true);
            newCustomer.setUptodate(LocalDateTime.now());
            customerRepository.save(newCustomer);
           return newCustomer;
        }
        return null;
    }

    public Customer updateCustomer(CustomerDto customerDto) {
        Optional<Customer> findCustomer = customerRepository.findById(customerDto.getId());
        if (customerDto != null && findCustomer.isEmpty()) {
            Customer newCustomer = new Customer();
            newCustomer = modelMapper.map(customerDto, Customer.class);
            newCustomer.setStatus(true);
            newCustomer.setUptodate(LocalDateTime.now());
            customerRepository.save(newCustomer);
             return newCustomer;
        }
       return null;
    }

    public Customer getCustomerById(Long Id) {
        Optional<Customer> customerUstId = customerRepository.findById(Id);
        return customerUstId.get();
    }

    public void deleteCustomer(Long id) {
        Optional<Customer> findCustomer = customerRepository.findById(id);
        Customer newCustomer = new Customer();
        if (findCustomer != null) {
            newCustomer = findCustomer.get();
            newCustomer.setStatus(false);
            newCustomer.setUptodate(LocalDateTime.now());
            customerRepository.save(newCustomer);
        }
    }

}
