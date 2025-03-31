package com.example.fuelmanager.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.fuelmanager.entity.FuelRecord;
import com.example.fuelmanager.entity.User;
import com.example.fuelmanager.repository.UserRepository;
import com.example.fuelmanager.service.FuelRecordService;

@Controller
@RequestMapping("/fuel-management")
public class FuelManagementController {

	private final FuelRecordService fuelRecordService;
	private final UserRepository userRepository; // UserRepository を追加

	public FuelManagementController(FuelRecordService fuelRecordService, UserRepository userRepository) {
		this.fuelRecordService = fuelRecordService;
		this.userRepository = userRepository;
	}

	// 📌 給油データを登録
	@PostMapping("/add")
	public String addFuelRecord(@RequestParam Long vehicleId,
			@RequestParam int odometer,
			@RequestParam double fuelAmount) {

		// 🔍 認証情報から現在のユーザー名を取得
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName(); // ユーザー名を取得

		// 🔍 ユーザー名から `User` エンティティを取得（OptionalからUserに変換）
		User user = userRepository.findByUsername(username).orElse(null);

		if (user != null) {
			fuelRecordService.addFuelRecord(vehicleId, odometer, fuelAmount, user);
			return "redirect:/fuel-management/" + vehicleId;
		}

		// ユーザーが見つからなければログイン画面へリダイレクト
		return "redirect:/login";
	}

	// ⛽ 燃費を計算して表示
	@GetMapping("/{vehicleId}")
	public String showFuelEfficiency(@PathVariable Long vehicleId, Model model) {
		double fuelEfficiency = fuelRecordService.calculateFuelEfficiency(vehicleId);
		List<FuelRecord> fuelRecords = fuelRecordService.getFuelRecords(vehicleId);
		
		FuelRecord latestRecord = fuelRecordService.getLatestFuelRecord(vehicleId);
		Double latestFuelEconomy = (latestRecord != null) ? latestRecord.getFuelEconomy() : null;
		
		model.addAttribute("fuelEfficiency", fuelEfficiency);
		model.addAttribute("latestFuelEconomy", latestFuelEconomy);
		model.addAttribute("fuelRecords", fuelRecords);
		model.addAttribute("vehicleId", vehicleId);
		return "vehicle/fuel-management";
	}
}
