package com.eranbackend.erandevu.repository;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eranbackend.erandevu.entity.Appointment;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    ArrayList<Appointment> findByUserIdAndDateBetween(Long id, LocalDate startDate, LocalDate endDate);
/* 
    @Query(value = "SELECT * FROM appointments WHERE user_id = :userId AND date BETWEEN :startDate AND :endDate", nativeQuery = true)
    ArrayList<Appointment> findAppointmentsByUserIdAndDateRange(@Param("userId") Long userId,
                                                                @Param("startDate") LocalDate startDate,
                                                                @Param("endDate") LocalDate endDate); */

}
