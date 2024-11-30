package com.eranbackend.erandevu.service;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eranbackend.erandevu.dto.AppointmentDto;
import com.eranbackend.erandevu.entity.Appointment;
import com.eranbackend.erandevu.repository.AppointmentRepository;

import lombok.RequiredArgsConstructor;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity<Map<String, Object>> saveAppointment(AppointmentDto appointmentDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (appointmentDto == null) {
                response.put("status", HttpStatus.BAD_REQUEST.value());
                response.put("message", "AppointmentDto cannot be null.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Appointment newAppointment = modelMapper.map(appointmentDto, Appointment.class);
            newAppointment.setStatus(true);
            newAppointment.setUptodate(LocalDateTime.now());
            Appointment savedAppointment = appointmentRepository.save(newAppointment);

            response.put("status", HttpStatus.CREATED.value());
            response.put("message", "Appointment saved successfully.");
            response.put("data", savedAppointment);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Failed to save appointment.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> updateAppointment(AppointmentDto appointmentDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Appointment> findAppointment = appointmentRepository.findById(appointmentDto.getId());
            if (findAppointment.isEmpty()) {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "Appointment not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Appointment existingAppointment = findAppointment.get();
            modelMapper.map(appointmentDto, existingAppointment);
            existingAppointment.setStatus(true);
            existingAppointment.setUptodate(LocalDateTime.now());
            Appointment updatedAppointment = appointmentRepository.save(existingAppointment);

            response.put("status", HttpStatus.OK.value());
            response.put("message", "Appointment updated successfully.");
            response.put("data", updatedAppointment);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Failed to update appointment.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, Object>> getAppointmentsByUserIdAndDateRange(Long userId,
                                                                                   LocalDateTime startDate,
                                                                                   LocalDateTime endDate) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Appointment> appointmentUserIdAndDate = appointmentRepository.findByUserIdAndAppointmentDateBetween(
                    userId, startDate, endDate);

            if (appointmentUserIdAndDate.isEmpty()) {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "No appointments found for the specified userId and date range.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Map<LocalDateTime, List<Appointment>> groupedByAppointmentDate = appointmentUserIdAndDate.stream()
                    .collect(Collectors.groupingBy(Appointment::getAppointmentDate));

            List<Map<String, Object>> result = new ArrayList<>();
            for (Map.Entry<LocalDateTime, List<Appointment>> entry : groupedByAppointmentDate.entrySet()) {
                Map<String, Object> group = new HashMap<>();
                group.put("groupAppointmentDate", entry.getKey());
                group.put("data", entry.getValue().stream()
                        .sorted(Comparator.comparing(Appointment::getHourArrayId))
                        .collect(Collectors.toList()));
                result.add(group);
            }

            response.put("status", HttpStatus.OK.value());
            response.put("message", "Appointments retrieved successfully.");
            response.put("data", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Failed to retrieve appointments.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> deleteAppointment(Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Appointment> findAppointment = appointmentRepository.findById(id);
            if (findAppointment.isEmpty()) {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "Appointment not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Appointment existingAppointment = findAppointment.get();
            existingAppointment.setStatus(false);
            existingAppointment.setUptodate(LocalDateTime.now());
            appointmentRepository.save(existingAppointment);

            response.put("status", HttpStatus.OK.value());
            response.put("message", "Appointment deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Failed to delete appointment.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
