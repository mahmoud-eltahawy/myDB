package com.db.tahawy.controllers;

import java.util.List;

import com.db.tahawy.model.FileModel;
import com.db.tahawy.model.User;
import com.db.tahawy.services.PublicService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {
	
	private final PasswordEncoder passwordEncoder;
	private final PublicService publicService;
	//files that other users published
	@GetMapping("/files")
	public List<FileModel> getPublicFiles(){
		return publicService.getUserpublicFiles();
	}

	@PostMapping("/createuser")
	public String setUser(@RequestParam("username")String userName,
			@RequestParam("password")String password) {
		User user = new User();
		user.setUserName(userName);
		user.setPassword(passwordEncoder.encode(password));
		publicService.saveUser(user);
		return "sucess";
	}
}
