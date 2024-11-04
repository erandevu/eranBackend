package com.eranbackend.erandevu.dto;

import java.time.LocalDateTime;

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
    private Long id;

    private Long userId;
    private Long costemerId;
    private Long hourArrayId;
    private String explanation;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime appointmentDate;

}
