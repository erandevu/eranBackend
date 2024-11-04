package com.eranbackend.erandevu.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eranbackend.erandevu.dto.HourTableDto;
import com.eranbackend.erandevu.entity.HourTable;
import com.eranbackend.erandevu.repository.HourTableRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class HourTableService {

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private HourTableRepository hourTableRepository;


    public HourTable saveHourTable(HourTableDto hourTableDto) {

        Optional<HourTable> findHourTable = hourTableRepository.findById(hourTableDto.getUserId());

        if (hourTableDto != null && findHourTable.isEmpty()) {
            HourTable newHourTable = new HourTable();
            newHourTable = modelMapper.map(hourTableDto, HourTable.class);
            newHourTable.setStatus(true);
            newHourTable.setUptodate(LocalDateTime.now());
            hourTableRepository.save(newHourTable);
           return newHourTable;
        }
        return null;
    }

    public HourTable updateHourTable(HourTableDto hourTableDto) {
        Optional<HourTable> findHourTable = hourTableRepository.findById(hourTableDto.getId());
        if (hourTableDto != null && findHourTable.isEmpty()) {
            HourTable newHourTable = new HourTable();
            newHourTable = modelMapper.map(hourTableDto, HourTable.class);
            newHourTable.setStatus(true);
            newHourTable.setUptodate(LocalDateTime.now());
            hourTableRepository.save(newHourTable);
             return newHourTable;
        }
       return null;
    }

    public HourTable getHourTableById(Long userId) {
        Optional<HourTable> hourTableUstId = hourTableRepository.findByUserId(userId);
        return hourTableUstId.get();
    }

    public void deleteHourTable(Long id) {
        Optional<HourTable> findHourTable = hourTableRepository.findById(id);
        HourTable newHourTable = new HourTable();
        if (findHourTable != null) {
            newHourTable = findHourTable.get();
            newHourTable.setStatus(false);
            newHourTable.setUptodate(LocalDateTime.now());
            hourTableRepository.save(newHourTable);
        }
    }

}
