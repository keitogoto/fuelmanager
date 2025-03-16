package com.example.fuelmanager.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.fuelmanager.entity.FuelRecord;
import com.example.fuelmanager.entity.User;
import com.example.fuelmanager.service.FuelRecordService;

@Controller
@RequestMapping("/fuel-management")
public class FuelManagementController {

	private final FuelRecordService fuelRecordService;

	public FuelManagementController(FuelRecordService fuelRecordService) {
		this.fuelRecordService = fuelRecordService;
	}

	// 📌 給油データを登録
	@PostMapping("/add")
	public String addFuelRecord(@RequestParam Long vehicleId,
			@RequestParam double odometer,
			@RequestParam double fuelAmount,
			@AuthenticationPrincipal User user) {
		fuelRecordService.addFuelRecord(vehicleId, odometer, fuelAmount, user);
		return "redirect:/fuel-management/" + vehicleId;
	}

	// ⛽ 燃費を計算して表示
	@GetMapping("/{vehicleId}")
	public String showFuelEfficiency(@PathVariable Long vehicleId, Model model) {
		double fuelEfficiency = fuelRecordService.calculateFuelEfficiency(vehicleId);
		List<FuelRecord> fuelRecords = fuelRecordService.getFuelRecords(vehicleId);

		model.addAttribute("fuelEfficiency", fuelEfficiency);
		model.addAttribute("fuelRecords", fuelRecords);
		model.addAttribute("vehicleId", vehicleId);
		return "fuel-management";
	}

}
