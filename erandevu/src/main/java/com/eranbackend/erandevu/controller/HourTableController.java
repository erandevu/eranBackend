package com.eranbackend.erandevu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.eranbackend.erandevu.dto.HourTableDto;
import com.eranbackend.erandevu.entity.HourTable;
import com.eranbackend.erandevu.service.HourTableService;


@RestController
@RequestMapping("/hour_table")
public class HourTableController {

    @Autowired
    private HourTableService hourTableService;

    @PostMapping
    public HourTable createHourTable(@RequestBody HourTableDto hourTableDto) {
        return hourTableService.saveHourTable(hourTableDto);
    }

    @PutMapping
    public HourTable updateHourTable(@RequestBody HourTableDto hourTableDto) {
        return hourTableService.updateHourTable(hourTableDto);
    }

    @GetMapping("user/{userId}")
    public HourTable getHourTableById(@PathVariable Long userId) {
        return hourTableService.getHourTableById(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteHourTable(@PathVariable Long id) {
        hourTableService.deleteHourTable(id);
    }

}
