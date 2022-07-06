package com.db.tahawy.dao;

import java.util.List;

import com.db.tahawy.model.LocalFile;
import com.db.tahawy.model.User;

public interface ProgramDao {

	 void setProgramRoot(String path);

	 void saveUser(User user);

	 User getUserByName(String userName);

	 void saveFile(LocalFile localFile);

	 List<LocalFile> getUserAllFiles();

	 List<LocalFile> getUserAllFilesByType(String type);

	 LocalFile getFileByName(String name);
	 
	 List<User> listUsers();
}
