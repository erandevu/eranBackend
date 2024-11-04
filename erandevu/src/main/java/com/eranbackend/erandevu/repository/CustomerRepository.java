package com.eranbackend.erandevu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eranbackend.erandevu.entity.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
