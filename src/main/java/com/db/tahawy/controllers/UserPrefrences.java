package com.db.tahawy.controllers;


import com.db.tahawy.model.User;
import com.db.tahawy.model.UserModel;
import com.db.tahawy.services.PrefrencesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

//installing steps
@RestController
@RequiredArgsConstructor
public class UserPrefrences {
	private final PrefrencesService PrefrencesService;
	private final PasswordEncoder passwordEncoder;
	private User user;
	//step 1    --> specify where the main folder of the program
	@PostMapping("/setroot")
	public String setHomeFolder(
			@RequestParam("path") String path,
			@RequestParam("password")String password) {
			PrefrencesService.setRoot(path,passwordEncoder.encode(password));
			return "sucess";
	}
	
	//step 2    --> create user 
	@RequestMapping("/createuser")
	public String setUser(@RequestParam("username")String userName,
			@RequestParam("password")String password) {
		User user = new User();
		user.setUserName(userName);
		user.setPassword(passwordEncoder.encode(password));
		PrefrencesService.saveUser(user);
		return "sucess";
	}
	
	@RequestMapping("inuser")
	public UserModel getCurrentUser() {
		return new UserModel(user.getUserName());
	}
}
