package com.eranbackend.erandevu.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eranbackend.erandevu.dto.AppointmentDto;
import com.eranbackend.erandevu.entity.Appointment;
import com.eranbackend.erandevu.repository.AppointmentRepository;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private AppointmentRepository appointmentRepository;


    public Appointment saveAppointment(AppointmentDto appointmentDto) {

        if (appointmentDto != null) {

            Appointment newAppointment = new Appointment();

            newAppointment = modelMapper.map(appointmentDto, Appointment.class);
            newAppointment.setStatus(true);
            newAppointment.setUptodate(LocalDateTime.now());
            appointmentRepository.save(newAppointment);

            return newAppointment;
        }
        return null;
    }

    public Appointment updateAppointment(AppointmentDto appointmentDto) {
        Optional<Appointment> findAppointment = appointmentRepository.findById(appointmentDto.getId());
        Appointment newAppointment = new Appointment();
        if (findAppointment.isPresent()) {
            newAppointment = modelMapper.map(appointmentDto, Appointment.class);
            newAppointment.setStatus(true);
            newAppointment.setUptodate(LocalDateTime.now());
            appointmentRepository.save(newAppointment);
        }
        return newAppointment;
    }

    public ResponseEntity<List<Map<String, Object>>> getAppointmentsByUserIdAndDateRange(Long userId, LocalDate startDate,
            LocalDate endDate) {

        ArrayList<Appointment> appointmentUserIdAndDate = appointmentRepository.findByUserIdAndDateBetween(userId,
                startDate, endDate);

                if (!appointmentUserIdAndDate.isEmpty()) {
                    // "AppointmentDate" alanına göre gruplama
                    Map<LocalDateTime, List<Appointment>> groupedByAppointmentDate = appointmentUserIdAndDate.stream()
                            .collect(Collectors.groupingBy(Appointment::getAppointmentDate));
        
                    // Her grubu istenen JSON formatına çevirme
                    List<Map<String, Object>> result = new ArrayList<>();
                    for (Map.Entry<LocalDateTime, List<Appointment>> entry : groupedByAppointmentDate.entrySet()) {
                        Map<String, Object> group = new HashMap<>();
                        group.put("groupAppointmentDate", entry.getKey());
        
                        // Randevuları hourArrayId'ye göre sıralama
                        List<Appointment> sortedAppointments = entry.getValue().stream()
                                .sorted(Comparator.comparing(Appointment::getHourArrayId))
                                .collect(Collectors.toList());

                        
                        group.put("data", sortedAppointments);
                        result.add(group);
                    }
            return ResponseEntity.ok(result);
        }
        return null;
    }

    public void deleteAppointment(Long id) {
        Optional<Appointment> findAppointment = appointmentRepository.findById(id);
        Appointment newAppointment = new Appointment();
        if (findAppointment.isPresent()) {
            newAppointment = findAppointment.get();
            newAppointment.setStatus(false);
            newAppointment.setUptodate(LocalDateTime.now());
            appointmentRepository.save(newAppointment);
        }
    }
}
