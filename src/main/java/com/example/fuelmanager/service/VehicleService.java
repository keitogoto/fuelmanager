package com.example.fuelmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.fuelmanager.entity.User;
import com.example.fuelmanager.entity.Vehicle;
import com.example.fuelmanager.repository.VehicleRepository;

@Service
public class VehicleService {

	private final VehicleRepository vehicleRepository;

	public VehicleService(VehicleRepository vehicleRepository) {
		this.vehicleRepository = vehicleRepository;
	}

	public List<Vehicle> findByUser(User user) {
		return vehicleRepository.findByUser(user);
	}

	public void saveVehicle(Vehicle vehicle) {
		vehicleRepository.save(vehicle);
	}
}
