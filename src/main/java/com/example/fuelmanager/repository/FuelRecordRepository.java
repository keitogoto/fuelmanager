package com.example.fuelmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fuelmanager.entity.FuelRecord;

public interface FuelRecordRepository extends JpaRepository<FuelRecord, Long> {
	List<FuelRecord> findByVehicleIdOrderByRefuelDateDesc(Long vehicleId);
}
