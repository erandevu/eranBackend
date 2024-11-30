package com.eranbackend.erandevu.repository;

import com.eranbackend.erandevu.entity.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {
}