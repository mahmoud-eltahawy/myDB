
package com.db.tahawy.controllers;

import com.db.tahawy.model.UserStatic;
import com.db.tahawy.services.PermissonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Permissons {
	@Autowired
	private PermissonService permissonService;
	
	@PostMapping("login")
	public String login(@RequestParam("username")String userName,
			@RequestParam("password")String password) {
		return permissonService.permitUser(userName,password);
	}
	
	@RequestMapping("exit")
	public String logout() {
		return permissonService.logout();
	}
	
	@RequestMapping("inuser")
	public String[] getCurrentUser() {
		String[] inuser = {UserStatic.getUserName(),UserStatic.getHome()};
		return inuser;
	}
}