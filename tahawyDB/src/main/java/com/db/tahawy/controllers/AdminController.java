package com.db.tahawy.controllers;


import java.util.List;
import java.util.stream.Collectors;

import com.db.tahawy.model.FileModel;
import com.db.tahawy.model.User;
import com.db.tahawy.model.UserModel;
import com.db.tahawy.services.AdminService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

//installing steps
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	private final AdminService PrefrencesService;
	private final PasswordEncoder passwordEncoder;
	//step 1    --> specify where the main folder of the program
	@PostMapping("/rootset")
	public String setHomeFolder(
			@RequestParam("path") String path,
			@RequestParam("password")String password) {
			PrefrencesService.setRoot(path,passwordEncoder.encode(password));
			return "sucess";
	}
	
	@RequestMapping("/lu")
	public List<UserModel> listUSers(){
		return PrefrencesService.getUsers()
				.stream().map(p -> new UserModel(p.getUserName()))
				.collect(Collectors.toList());
	}
	
	@GetMapping("/filesbytype")
	public List<FileModel> getAllFilesByType(@RequestParam("type")String type){
		return PrefrencesService.getAllFilesByType(type);
	}
	
	@RequestMapping("/lt")
	public List<String> listTypes(){
		return PrefrencesService.getAllTypes();
	}
}
