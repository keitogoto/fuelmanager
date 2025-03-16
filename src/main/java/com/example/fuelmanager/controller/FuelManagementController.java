package com.example.fuelmanager.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.fuelmanager.entity.User;
import com.example.fuelmanager.entity.Vehicle;
import com.example.fuelmanager.service.UserService;
import com.example.fuelmanager.service.VehicleService;

@Controller
public class FuelManagementController {

	private final VehicleService vehicleService;
	private final UserService userService;

	public FuelManagementController(VehicleService vehicleService, UserService userService) {
		this.vehicleService = vehicleService;
		this.userService = userService;
	}

	@GetMapping("/fuel-management/{vehicleId}")
	public String manageFuel(@PathVariable Long vehicleId, Model model) {
		Vehicle vehicle = vehicleService.findById(vehicleId);
		if (vehicle == null) {
			return "error/404"; // 車両が存在しない場合のエラーページ
		}

		// ログインユーザーを取得
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User loggedInUser = userService.findByUsername(username);

		// 車両の所有者チェック
		if (!vehicle.getUser().getId().equals(loggedInUser.getId())) {
			return "error/403"; // 他人の車両にアクセスしようとした場合
		}

		model.addAttribute("vehicle", vehicle);
		return "vehicle/fuel-management";
	}
}
