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

@Entity
@Table(name = "fuel_records")
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
	private LocalDate refuelDate; // 給油日

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	// --- コンストラクタ ---
	public FuelRecord() {
	}

	public FuelRecord(Vehicle vehicle, double odometer, double fuelAmount, LocalDate refuelDate, User user) {
		this.vehicle = vehicle;
		this.odometer = odometer;
		this.fuelAmount = fuelAmount;
		this.refuelDate = refuelDate;
		this.user = user;
	}

	// --- Getter & Setter ---
	public Long getId() {
		return id;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public double getOdometer() {
		return odometer;
	}

	public void setOdometer(double odometer) {
		this.odometer = odometer;
	}

	public double getFuelAmount() {
		return fuelAmount;
	}

	public void setFuelAmount(double fuelAmount) {
		this.fuelAmount = fuelAmount;
	}

	public LocalDate getRefuelDate() {
		return refuelDate;
	}

	public void setRefuelDate(LocalDate refuelDate) {
		this.refuelDate = refuelDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
