package com.eranbackend.erandevu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eranbackend.erandevu.dto.AppointmentDto;
import com.eranbackend.erandevu.entity.Appointment;
import com.eranbackend.erandevu.service.AppointmentService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public Appointment createAppointment(@RequestBody AppointmentDto appointmentDto) {
        return appointmentService.saveAppointment(appointmentDto);
    }

    @PutMapping
    public Appointment updateAppointment(@RequestBody AppointmentDto appointmentDto) {
        return appointmentService.updateAppointment(appointmentDto);
    }

    @GetMapping("user/{userId}/start_date/{startDate}/end_date/{endDate}")
    public ResponseEntity<List<Map<String, Object>>> getAppointmentById(@PathVariable Long userId,LocalDate startDate,
            LocalDate endDate) {
        return appointmentService.getAppointmentsByUserIdAndDateRange(userId, startDate, endDate);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }

}
