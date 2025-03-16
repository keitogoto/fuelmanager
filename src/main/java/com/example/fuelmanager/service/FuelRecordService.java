package com.example.fuelmanager.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fuelmanager.entity.FuelRecord;
import com.example.fuelmanager.entity.User;
import com.example.fuelmanager.entity.Vehicle;
import com.example.fuelmanager.repository.FuelRecordRepository;
import com.example.fuelmanager.repository.VehicleRepository;

@Service
public class FuelRecordService {

	private final FuelRecordRepository fuelRecordRepository;
	private final VehicleRepository vehicleRepository;

	public FuelRecordService(FuelRecordRepository fuelRecordRepository, VehicleRepository vehicleRepository) {
		this.fuelRecordRepository = fuelRecordRepository;
		this.vehicleRepository = vehicleRepository;
	}

	// 🚗 給油データを登録
	@Transactional
	public void addFuelRecord(Long vehicleId, double odometer, double fuelAmount, User user) {
		Vehicle vehicle = vehicleRepository.findById(vehicleId)
				.orElseThrow(() -> new IllegalArgumentException("車両が見つかりません"));

		FuelRecord record = new FuelRecord(vehicle, odometer, fuelAmount, LocalDate.now(), user);
		fuelRecordRepository.save(record);
	}

	// ⛽ 燃費を計算 (直近の2回の給油データを使用)
	public double calculateFuelEfficiency(Long vehicleId) {
		List<FuelRecord> records = fuelRecordRepository.findByVehicleIdOrderByRefuelDateDesc(vehicleId);
		if (records.size() < 2) {
			return 0; // 給油履歴が2件未満なら計算不可
		}
		FuelRecord latest = records.get(0);
		FuelRecord previous = records.get(1);

		double distance = latest.getOdometer() - previous.getOdometer();
		double fuelUsed = latest.getFuelAmount();

		return (fuelUsed > 0) ? distance / fuelUsed : 0;
	}

	// 🚗 給油履歴を取得
	public List<FuelRecord> getFuelRecords(Long vehicleId) {
		return fuelRecordRepository.findByVehicleIdOrderByRefuelDateDesc(vehicleId);
	}

}
