package com.eranbackend.erandevu.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.eranbackend.erandevu.utils.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    @Id
    @GeneratedValue
    private UUID id;

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
