
package com.db.tahawy.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.db.tahawy.dao.ProgramDao;
import com.db.tahawy.model.LocalFile;
import com.db.tahawy.model.User;
import com.db.tahawy.model.UserStatic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

@Service
public class OperationsService {

	@Autowired
	private ProgramDao programDao;
	// persist data in database also 
	public String getSuitablePlace(String fileType,String fileName) {
		if(fileType.isEmpty()) {
			fileType = "default";
		}
		String typePath = UserStatic.getHome()+"/."+fileType;
		File file = new File(typePath);
		file.mkdirs();
		programDao.saveFile(new LocalFile(UserStatic.getUserName(), fileName, typePath, fileType));
		return typePath;
	}

	public List<LocalFile> getUserAllFiles() {
		return programDao.getUserAllFiles();
	}

	public List<LocalFile> getUserAllFilesByType(String type) {
		return programDao.getUserAllFilesByType(type);
	}

	public LocalFile getFileByName(String name) {
		return programDao.getFileByName(name);
	}

	public void downloader(LocalFile file0, HttpServletResponse response) throws IOException {
		File file = new File(file0.getPath());
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());

		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}

		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("attachment; filename=" + file.getName()));
		response.setContentLength((int) file.length());

		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}

	public List<User> getUsers() {
		List<User> users = programDao.listUsers();
		for (User user : users) {
			user.setPassword("************");
		}
		return users;
	}

	public String getSuitablePlaceOnly(String fileType, String fileName) {
		if(fileType.isEmpty()) {
			fileType = "default";
		}
		String typePath = UserStatic.getHome()+"/."+fileType;
		File file = new File(typePath);
		file.mkdirs();
		return typePath;
	}
}