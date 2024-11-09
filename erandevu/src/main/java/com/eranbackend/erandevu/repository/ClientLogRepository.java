package com.eranbackend.erandevu.repository;

import com.eranbackend.erandevu.entity.ClientLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientLogRepository extends JpaRepository<ClientLog, Long> {
    List<ClientLog> findByUserIdAndCostemerIdOrderByCreatedDateDesc(Long userId, Long costemerId);

}