
package com.db.tahawy.services;

import java.io.File;

import com.db.tahawy.dao.ProgramDao;
import com.db.tahawy.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrefrencesService {
	@Autowired
	private ProgramDao programDao;
	
	public void setProgramRoot(String path) {
		String programHome = path;
		File file = new File(programHome);
		file.mkdirs();
		programDao.setProgramRoot(path);
	}
	
	//can't be executed without initializing program home
	public void saveUser(User user) {
		String programHome = programDao.getUserByName("root").getHome();
		String userHome = programHome+"/."+user.getUserName();
		File file = new File(userHome);
		file.mkdir();
		user.setHome(userHome);
		programDao.saveUser(user);
	}
}