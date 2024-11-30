package com.eranbackend.erandevu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "error_logs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorLog {

    @Id
   @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "error_code")
    private String errorCode;

    @Column(name = "message")
    private String message;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;


    public ErrorLog(String errorCode, String message, LocalDateTime timestamp) {
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = timestamp;
    }
}
