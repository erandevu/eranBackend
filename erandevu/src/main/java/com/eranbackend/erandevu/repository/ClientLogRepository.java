package com.eranbackend.erandevu.repository;

import com.eranbackend.erandevu.entity.ClientLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClientLogRepository extends JpaRepository<ClientLog, UUID> {
    List<ClientLog> findByUserIdAndCostemerIdOrderByCreatedDateDesc(UUID userId, UUID costemerId);

}