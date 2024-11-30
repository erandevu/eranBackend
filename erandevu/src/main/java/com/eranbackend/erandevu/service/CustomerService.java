package com.eranbackend.erandevu.service;

import com.eranbackend.erandevu.dto.CustomerDto;
import com.eranbackend.erandevu.entity.ClientLog;
import com.eranbackend.erandevu.entity.Customer;
import com.eranbackend.erandevu.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ClientLogService clientLogService;
    private final ModelMapper modelMapper;

    public ResponseEntity<Map<String, Object>> saveCustomer(CustomerDto customerDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (customerDto == null) {
                response.put("status", HttpStatus.BAD_REQUEST.value());
                response.put("message", "CustomerDto cannot be null.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Optional<Customer> findCustomer = customerRepository.findById(customerDto.getId());
            if (findCustomer.isPresent()) {
                response.put("status", HttpStatus.CONFLICT.value());
                response.put("message", "Customer with this ID already exists.");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            Customer newCustomer = modelMapper.map(customerDto, Customer.class);
            newCustomer.setStatus(true);
            newCustomer.setUptodate(LocalDateTime.now());
            Customer savedCustomer = customerRepository.save(newCustomer);

            logClientAction(customerDto, "saveCustomer", "Customer saved successfully.");

            response.put("status", HttpStatus.CREATED.value());
            response.put("message", "Customer saved successfully.");
            response.put("data", savedCustomer);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Failed to save customer.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Map<String, Object>> updateCustomer(CustomerDto customerDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (customerDto == null) {
                response.put("status", HttpStatus.BAD_REQUEST.value());
                response.put("message", "CustomerDto cannot be null.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Optional<Customer> findCustomer = customerRepository.findById(customerDto.getId());
            if (findCustomer.isEmpty()) {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "Customer not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Customer existingCustomer = findCustomer.get();
            modelMapper.map(customerDto, existingCustomer);
            existingCustomer.setStatus(true);
            existingCustomer.setUptodate(LocalDateTime.now());
            Customer updatedCustomer = customerRepository.save(existingCustomer);

            logClientAction(customerDto, "updateCustomer", "Customer updated successfully.");

            response.put("status", HttpStatus.OK.value());
            response.put("message", "Customer updated successfully.");
            response.put("data", updatedCustomer);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Failed to update customer.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, Object>> getCustomerById(Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Customer> customer = customerRepository.findById(id);
            if (customer.isEmpty()) {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "Customer not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            response.put("status", HttpStatus.OK.value());
            response.put("message", "Customer retrieved successfully.");
            response.put("data", customer.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Failed to retrieve customer.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Map<String, Object>> deleteCustomer(Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Customer> findCustomer = customerRepository.findById(id);
            if (findCustomer.isEmpty()) {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "Customer not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Customer existingCustomer = findCustomer.get();
            existingCustomer.setStatus(false);
            existingCustomer.setUptodate(LocalDateTime.now());
            customerRepository.save(existingCustomer);

            logClientAction(null, "deleteCustomer", "Customer deleted successfully.");

            response.put("status", HttpStatus.OK.value());
            response.put("message", "Customer deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Failed to delete customer.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    private void logClientAction(CustomerDto customerDto, String methodName, String description) {
        try {
            ClientLog clientLog = new ClientLog();
            if (customerDto != null) {
                clientLog.setCostemerId(customerDto.getId());
                clientLog.setDescription(customerDto.toString());
            }
            clientLog.setClassName("CustomerService");
            clientLog.setMethodName(methodName);
            clientLog.setDescription(description);
            clientLogService.saveClientLog(clientLog);
        } catch (Exception e) {
            // Loglama işlemi başarısız olsa bile ana iş akışı kesilmez.
            e.printStackTrace();
        }
    }
}
