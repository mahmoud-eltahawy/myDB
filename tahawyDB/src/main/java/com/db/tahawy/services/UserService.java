package com.db.tahawy.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import com.db.tahawy.dao.FileJpa;
import com.db.tahawy.dao.UserFileJpa;
import com.db.tahawy.dao.UserJpa;
import com.db.tahawy.model.FileModel;
import com.db.tahawy.model.LocalFile;
import com.db.tahawy.model.User;
import com.db.tahawy.model.UserFile;
import com.db.tahawy.model.UserFileId;
import com.db.tahawy.model.UserModel;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserJpa userJpa;
	private final FileJpa fileJpa;
	private final UserFileJpa userFileJpa;

	private User user() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userJpa.findById(userDetails.getUsername()).get();
	};
	public String getSuitablePlace(String fileType,String fileName) {
		if(fileType.isEmpty()) {
			fileType = "default";
		}
		String typePath = user().getHome()+"/."+fileType;
		File file = new File(typePath);
		file.mkdirs();
		return typePath;
	}
	
	public void saveFile(LocalFile file) {
		fileJpa.save(file);
	}

	private List<FileModel> mapToModel(List<LocalFile> files){
		return files.stream().map(p -> FileModel.builder()
						.fileName(p.getFileName())
						.fileType(p.getFileType())
						.isPublic(p.getIsPublic())
						.usermodel(new UserModel(p.getUser().getUserName()))
						.build()).collect(Collectors.toList());
	}
	
	public List<FileModel> getUserprivateFiles() {
		return mapToModel(fileJpa.findUserPrivateFiles(user().getUserName()));
	}

	public List<FileModel> getUserSentFiles() {
		return mapToModel(fileJpa.findUserSentFiles(user().getUserName()));
	}
	
	public List<FileModel> getUserRecivedFiles() {
		return mapToModel(fileJpa.findUserRecivedFiles(user().getUserName()));
	}
	

	public List<FileModel> getUserPublishedFiles() {
		return mapToModel(fileJpa.findPublishedFiles(user().getUserName()));
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

	public void share(String filename, String username) {
		LocalFile file = fileJpa.findById(filename).get();
		User user = userJpa.findById(username).get();
		UserFileId userFileId = new UserFileId(user, file);
		if(username.equals(file.getUser().getUserName())) {
			userFileJpa.deleteById(userFileId);
		} else {
			userFileJpa.save(new UserFile(userFileId));
		}
	}

	public void publish(String filename) {
		if(fileJpa.isFilePublic(filename)) {
			fileJpa.depublish(filename);
		} else {
			fileJpa.publish(filename);
		}
	}

	public User getUserByName(String username) {
		return userJpa.findById(username).get();
	}
}