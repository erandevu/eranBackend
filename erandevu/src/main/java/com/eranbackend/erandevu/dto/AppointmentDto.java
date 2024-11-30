package com.eranbackend.erandevu.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentDto {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID userId;
    private UUID costemerId;
    private String explanation;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime appointmentDate;

}
