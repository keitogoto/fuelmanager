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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FuelRecordService {

	private final FuelRecordRepository fuelRecordRepository;
	private final VehicleRepository vehicleRepository;

	// 🚗 給油データを登録
	@Transactional
	public void addFuelRecord(Long vehicleId, int odometer, double fuelAmount, User user) {
	    Vehicle vehicle = vehicleRepository.findById(vehicleId)
	            .orElseThrow(() -> new IllegalArgumentException("車両が見つかりません"));

	    // 🚗 Vehicleから直接前回ODOを取得
	    double previousOdometer = vehicle.getOdometer();

	    // ⛽ 距離計算・燃費算出
	    double distance = odometer - previousOdometer;
	    double fuelEconomy = (fuelAmount > 0 && distance > 0) ? distance / fuelAmount : 0;

	    // 🚗 給油データ保存
	    FuelRecord record = new FuelRecord(vehicle, odometer, fuelAmount, fuelEconomy, user, LocalDate.now());
	    fuelRecordRepository.save(record);

	    // 🔄 車両のODO更新
	    vehicle.setOdometer(odometer);
	    vehicleRepository.save(vehicle);
	}

	// ⛽ 全履歴の平均燃費を算出
	public double calculateFuelEfficiency(Long vehicleId) {
	    List<FuelRecord> records = fuelRecordRepository.findByVehicleIdOrderByRefuelDateDesc(vehicleId);
	    
	    if (records.isEmpty()) {
	        return 0.0;
	    }

	    if (records.size() == 1) {
	        Double economy = records.get(0).getFuelEconomy();
	        return (economy != null) ? economy : 0.0;
	    }

	    double total = 0.0;
	    int count = 0;

	    for (FuelRecord record : records) {
	        Double economy = record.getFuelEconomy();
	        if (economy != null && economy > 0) {
	            total += economy;
	            count++;
	        }
	    }

	    return (count > 0) ? total / count : 0.0;
	}



	// 🚗 給油履歴を取得
	public List<FuelRecord> getFuelRecords(Long vehicleId) {
		return fuelRecordRepository.findByVehicleIdOrderByRefuelDateDesc(vehicleId);
	}
	
	public FuelRecord getLatestFuelRecord(Long vehicleId) {
	    return fuelRecordRepository.findTopByVehicleIdOrderByRefuelDateDesc(vehicleId);
	}

}