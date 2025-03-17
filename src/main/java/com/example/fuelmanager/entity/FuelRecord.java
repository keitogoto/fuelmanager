package com.example.fuelmanager.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fuel_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuelRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "vehicle_id", nullable = false)
	private Vehicle vehicle;

	@Column(nullable = false)
	private double odometer; // ODOメーターの値

	@Column(nullable = false)
	private double fuelAmount; // 給油量

	@Column(nullable = false)
	private double fuelEconomy; // 燃費

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false)
	private LocalDate refuelDate; // 給油日

	// ✅ id なしのコンストラクタを手動追加
	public FuelRecord(Vehicle vehicle, double odometer, double fuelAmount, double fuelEconomy, User user,
			LocalDate refuelDate) {
		this.vehicle = vehicle;
		this.odometer = odometer;
		this.fuelAmount = fuelAmount;
		this.fuelEconomy = fuelEconomy;
		this.user = user;
		this.refuelDate = refuelDate;
	}

}
