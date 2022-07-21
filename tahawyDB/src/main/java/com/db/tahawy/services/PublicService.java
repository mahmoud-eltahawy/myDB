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

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicService {
	
	private final FileJpa fileJpa;
	private final UserJpa userJpa;


	private List<FileModel> mapToModel(List<LocalFile> files){
		return files.stream().map(p -> FileModel.builder()
						.fileName(p.getFileName())
						.fileType(p.getFileType())
						.isPublic(p.getIsPublic())
						.usermodel(new UserModel(p.getUser().getUserName()))
						.build()).collect(Collectors.toList());
	}
	
	public List<FileModel> getUserpublicFiles() {
		return mapToModel(fileJpa.findFileByIsPublic(true));
	}

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
