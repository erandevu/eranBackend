package com.eranbackend.erandevu.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
@Table(name = "hourTable")
public class HourTable {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    @JsonFormat(pattern = "HH:mm")
    private ArrayList <LocalDateTime> timeIntervals;

    private Boolean status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime uptodate;

 
}
