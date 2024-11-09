package com.eranbackend.erandevu.controller;

import com.eranbackend.erandevu.entity.ClientLog;
import com.eranbackend.erandevu.service.ClientLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/clientLogs")
@RequiredArgsConstructor
public class ClientLogController {

    private final ClientLogService clientLogService;



    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveClientLog(@RequestBody ClientLog clientLog) {
        return clientLogService.saveClientLog(clientLog);
    }

    @GetMapping("/getByUserIdAndCustomerId")
    public ResponseEntity<Map<String, Object>> getClientLogsByUserIdAndCustomerId(
            @RequestParam Long userId,
            @RequestParam Long costemerId) {
        return clientLogService.getClientLogsByUserIdAndCustomerId(userId, costemerId);
    }
}