package com.db.tahawy.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.db.tahawy.dao.FileJpa;
import com.db.tahawy.dao.UserJpa;
import com.db.tahawy.model.LocalFile;
import com.db.tahawy.model.User;
import com.db.tahawy.model.UserStatic;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OperationsService {

	private UserJpa userJpa;
	private FileJpa fileJpa;
	// persist data in database also 
	public String getSuitablePlace(String fileType,String fileName) {
		if(fileType.isEmpty()) {
			fileType = "default";
		}
		String typePath = UserStatic.getHome()+"/."+fileType;
		File file = new File(typePath);
		file.mkdirs();
		fileJpa.save(new LocalFile(fileName,UserStatic.getUser(), typePath, fileType));
		return typePath;
	}

	public List<LocalFile> getUserAllFiles() {
		return userJpa.findById(UserStatic.getUserName()).get().getFiles();
	}

	// TODO
	public List<LocalFile> getUserAllFilesByType(String type) {
		return fileJpa.findByFileType(type);
	}

	public LocalFile getFileByName(String name) {
		return fileJpa.findById(name).get();
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
		List<User> users = userJpa.findAll();
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