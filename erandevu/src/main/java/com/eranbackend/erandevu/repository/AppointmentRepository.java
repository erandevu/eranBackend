package com.eranbackend.erandevu.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eranbackend.erandevu.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    ArrayList<Appointment> findByUserIdAndAppointmentDateBetween(UUID id, LocalDateTime startDate, LocalDateTime endDate);
    /*
     * @Query(value =
     * "SELECT * FROM appointments WHERE user_id = :userId AND date BETWEEN :startDate AND :endDate"
     * , nativeQuery = true)
     * ArrayList<Appointment> findAppointmentsByUserIdAndDateRange(@Param("userId")
     * UUID userId,
     * 
     * @Param("startDate") LocalDate startDate,
     * 
     * @Param("endDate") LocalDate endDate);
     */


}
