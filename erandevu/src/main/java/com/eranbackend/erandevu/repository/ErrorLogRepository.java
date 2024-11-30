package com.eranbackend.erandevu.repository;

import com.eranbackend.erandevu.entity.ErrorLog;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorLogRepository extends JpaRepository<ErrorLog, UUID> {
}