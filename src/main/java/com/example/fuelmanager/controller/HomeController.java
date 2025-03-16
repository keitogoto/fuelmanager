package com.example.fuelmanager.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.fuelmanager.entity.User;
import com.example.fuelmanager.entity.Vehicle;
import com.example.fuelmanager.service.UserService;
import com.example.fuelmanager.service.VehicleService;

@Controller
public class HomeController {

	private final VehicleService vehicleService;
	private final UserService userService;

	public HomeController(VehicleService vehicleService, UserService userService) {
		this.vehicleService = vehicleService;
		this.userService = userService;
	}

	@GetMapping("/home")
	public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		// ログインユーザーの情報を取得
		User user = userService.findByUsername(userDetails.getUsername());

		// ユーザーに紐づく車両を取得
		List<Vehicle> vehicles = vehicleService.findByUser(user);
		model.addAttribute("vehicles", vehicles);

		return "home";
	}
}
