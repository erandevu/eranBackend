package com.eranbackend.erandevu.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.eranbackend.erandevu.utils.UserRole;
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
@Table(name = "usr")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    /* @Column(nullable = false) */
    private String firstName;
    /* @Column(nullable = false) */
    private String lastName;
    /* @Column(nullable = false, unique = true) */
    private String email;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt;
    private Boolean status;
    private UserRole userRole;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime uptodate;

}
