package com.example.fuelmanager.service;

import java.util.List;
import java.util.Optional;

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

	public Vehicle findById(Long id) {
		Optional<Vehicle> vehicle = vehicleRepository.findById(id);
		return vehicle.orElse(null); // 見つからなかった場合は null を返す
	}
}
