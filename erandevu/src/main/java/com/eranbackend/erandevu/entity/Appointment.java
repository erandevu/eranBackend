package com.eranbackend.erandevu.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private Long costemerId;
    private Long hourArrayId;
    private String explanation;
    private Boolean status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime uptodate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime appointmentDate;

}
