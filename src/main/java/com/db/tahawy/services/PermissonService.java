package com.db.tahawy.services;


import com.db.tahawy.dao.ProgramDao;
import com.db.tahawy.model.User;
import com.db.tahawy.model.UserStatic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissonService {
	@Autowired
	private ProgramDao programDao;
	
	public String permitUser(String userName, String password) {
		User user = programDao.getUserByName(userName);
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
