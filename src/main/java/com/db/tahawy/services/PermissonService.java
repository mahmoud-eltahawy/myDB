package com.db.tahawy.services;


import com.db.tahawy.dao.UserJpa;
import com.db.tahawy.model.User;
import com.db.tahawy.model.UserStatic;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PermissonService {
	
	private UserJpa userJpa;
	
	public String permitUser(String userName, String password) {
		User user = userJpa.findById(userName).get();
		if(user.getPassword().equals(password)) {
			UserStatic.setUser(user);
			return "success .  you are in";
		}
		return "failure . try again";
	}

	public String logout() {
		UserStatic.freeUser();
		return "you are out";
	}
}
