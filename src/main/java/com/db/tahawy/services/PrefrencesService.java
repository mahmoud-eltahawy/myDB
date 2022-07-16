package com.db.tahawy.services;

import java.io.File;

import com.db.tahawy.dao.UserJpa;
import com.db.tahawy.model.User;
import com.db.tahawy.security.AppUserRole;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrefrencesService {
	private final UserJpa userJpa;
	
	public void setRoot(String path,String password) {
		String programHome = path;
		File file = new File(programHome);
		file.mkdirs();
		userJpa.save(new User( "root", password, path,AppUserRole.SUPER_USER.name()));
	}
	
	//can't be executed without initializing program home
	public void saveUser(User user) {
		String programHome = userJpa.findById("root").get().getHome();
		String userHome = programHome+"/."+user.getUserName();
		File file = new File(userHome);
		file.mkdir();
		user.setHome(userHome);
		user.setPassword(user.getPassword());
		userJpa.save(user);
	}
}