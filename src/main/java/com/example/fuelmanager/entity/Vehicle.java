package com.example.fuelmanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name; // 車両名
	private String model; // モデル
	private String manufacturer; // メーカー
	private int year; // 年式
	private int odoMeter; // 初期ODOメーター（走行距離）

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user; // 車両の所有者
}
