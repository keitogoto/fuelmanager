package com.example.fuelmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.fuelmanager.entity.User;
import com.example.fuelmanager.entity.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	List<Vehicle> findByUserId(Long userId);

	List<Vehicle> findByUser(User user);
}
