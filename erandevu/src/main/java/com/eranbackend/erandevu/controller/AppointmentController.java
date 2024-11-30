package com.eranbackend.erandevu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.eranbackend.erandevu.dto.AppointmentDto;
import com.eranbackend.erandevu.service.AppointmentService;

import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/Appointment")
@RequiredArgsConstructor
public class AppointmentController {

   
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createAppointment(@RequestBody AppointmentDto appointmentDto) {
        return appointmentService.saveAppointment(appointmentDto);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateAppointment(@RequestBody AppointmentDto appointmentDto) {
        return appointmentService.updateAppointment(appointmentDto);
    }

    @GetMapping("user/{userId}/start_date/{startDate}/end_date/{endDate}")
    public ResponseEntity<Map<String, Object>> getAppointmentById(@PathVariable UUID userId, @PathVariable LocalDateTime startDate,
    @PathVariable LocalDateTime endDate) {
        return appointmentService.getAppointmentsByUserIdAndDateRange(userId, startDate, endDate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteAppointment(@PathVariable UUID id) {
        return appointmentService.deleteAppointment(id);
    }

}
