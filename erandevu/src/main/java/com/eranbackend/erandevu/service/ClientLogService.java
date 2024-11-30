package com.eranbackend.erandevu.service;

import com.eranbackend.erandevu.entity.ClientLog;
import com.eranbackend.erandevu.repository.ClientLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientLogService {

    private final ClientLogRepository clientLogRepository;

    public ResponseEntity<Map<String, Object>> saveClientLog(ClientLog clientLog) {
        Map<String, Object> response = new HashMap<>();
        try {
            ClientLog savedLog = clientLogRepository.save(clientLog);
            response.put("status", HttpStatus.CREATED.value());
            response.put("message", "ClientLog saved successfully.");
            response.put("data", savedLog);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Failed to save ClientLog.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, Object>> getClientLogsByUserIdAndCustomerId(UUID userId, UUID costemerId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ClientLog> logs = clientLogRepository.findByUserIdAndCostemerIdOrderByCreatedDateDesc(userId, costemerId);
            if (logs.isEmpty()) {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "No ClientLogs found for the specified userId and customerId.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.put("status", HttpStatus.OK.value());
            response.put("message", "ClientLogs retrieved successfully.");
            response.put("data", logs);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Failed to retrieve ClientLogs.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }



}
