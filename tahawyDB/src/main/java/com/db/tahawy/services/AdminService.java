package com.db.tahawy.services;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import com.db.tahawy.dao.FileJpa;
import com.db.tahawy.dao.UserJpa;
import com.db.tahawy.model.FileModel;
import com.db.tahawy.model.LocalFile;
import com.db.tahawy.model.User;
import com.db.tahawy.model.UserModel;
import com.db.tahawy.security.AppUserRole;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	private final UserJpa userJpa;
	private final FileJpa fileJpa;
	
	public void setRoot(String path,String password) {
		String programHome = path;
		File file = new File(programHome);
		file.mkdirs();
		userJpa.save(new User( "root", password, path,AppUserRole.ADMIN.name()));
	}

	public List<User> getUsers() {
		List<User> users = userJpa.findAll();
		for (User user : users) {
			user.setPassword("************");
		}
		return users;
	}
	
	private List<FileModel> mapToModel(List<LocalFile> files){
		return files.stream().map(p -> FileModel.builder()
						.fileName(p.getFileName())
						.fileType(p.getFileType())
						.isPublic(p.getIsPublic())
						.usermodel(new UserModel(p.getUser().getUserName()))
						.build()).collect(Collectors.toList());
	}
	
	public List<FileModel> getAllFilesByType(String type) {
		return mapToModel(fileJpa.findByFileTypeContaining(type));
	}
	
	public List<String> getAllTypes(){
		return fileJpa.findAllTypes();
	}
}