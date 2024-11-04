package com.eranbackend.erandevu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eranbackend.erandevu.entity.HourTable;


public interface HourTableRepository extends JpaRepository<HourTable, Long> {

    Optional<HourTable> findByUserId(Long userId);

}
