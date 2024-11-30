package com.eranbackend.erandevu.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eranbackend.erandevu.entity.User;


public interface UserRepository extends JpaRepository<User, UUID> {

}
