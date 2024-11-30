package com.eranbackend.erandevu.service;

import com.eranbackend.erandevu.entity.ErrorLog;
import com.eranbackend.erandevu.repository.ErrorLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ErrorLogService {

    private final ErrorLogRepository errorLogRepository;

    @Autowired
    public ErrorLogService(ErrorLogRepository errorLogRepository) {
        this.errorLogRepository = errorLogRepository;
    }

    public void logError(String errorCode, String message) {
        ErrorLog errorLog = new ErrorLog(errorCode, message, LocalDateTime.now());
        errorLogRepository.save(errorLog);
    }
}
