package com.db.tahawy.controllers;


import com.db.tahawy.model.User;
import com.db.tahawy.services.PrefrencesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//installing steps
@RestController
public class UserPrefrences {
	@Autowired
	private PrefrencesService PrefrencesService;
	
	//step 1    --> specify where the main folder of the program
	@GetMapping("/setroot")
	public String setHomeFolder(
			@RequestParam("path") String path,
			@RequestParam("password")String password) {
			PrefrencesService.setRoot(path,password);
			return "sucess";
	}
	
	//step 2    --> create user 
	@PostMapping("createuser")
	public String setUser(@RequestParam("username")String userName,
			@RequestParam("password")String password) {
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		PrefrencesService.saveUser(user);
		return "sucess";
	}
}
