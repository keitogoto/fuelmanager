package com.example.fuelmanager.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.fuelmanager.entity.User;
import com.example.fuelmanager.entity.Vehicle;
import com.example.fuelmanager.service.UserService;
import com.example.fuelmanager.service.VehicleService;

@Controller
@RequestMapping("/vehicle")
public class VehicleController {

	private final VehicleService vehicleService;
	private final UserService userService;

	public VehicleController(VehicleService vehicleService, UserService userService) {
		this.vehicleService = vehicleService;
		this.userService = userService;
	}

	// 車両登録フォームを表示
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("vehicle", new Vehicle());
		return "vehicle/register";
	}

	// 車両を登録
	@PostMapping("/register")
	public String registerVehicle(@ModelAttribute Vehicle vehicle,
			@AuthenticationPrincipal UserDetails userDetails) {
		// ログインユーザーを取得
		User user = userService.findByUsername(userDetails.getUsername());

		// ユーザーをセット
		vehicle.setUser(user);

		// 車両情報を保存
		vehicleService.saveVehicle(vehicle);

		// ホームにリダイレクト
		return "redirect:/home";
	}
}
