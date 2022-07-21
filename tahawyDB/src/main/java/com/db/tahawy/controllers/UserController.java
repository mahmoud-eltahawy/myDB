package com.db.tahawy.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.db.tahawy.model.FileModel;
import com.db.tahawy.model.LocalFile;
import com.db.tahawy.model.User;
import com.db.tahawy.model.UserModel;
import com.db.tahawy.services.UserService;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService operationService;
	
	private String getCurrentUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			  return ((UserDetails)principal).getUsername();
			} else {
			  return principal.toString();
			}
	}
	
	@RequestMapping("/inuser")
	public UserModel getCurrentUser() {
		String username = getCurrentUsername();
		return new UserModel(username);
	}
	
	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file")MultipartFile file) throws IllegalStateException, IOException {
		String type = file.getContentType();
		String fileName = file.getOriginalFilename();
		String place = operationService.getSuitablePlace(type,fileName);
		String username = getCurrentUsername();
		User user = operationService.getUserByName(username);
		LocalFile file0 = LocalFile.builder()
				.fileName(fileName)
				.fileType(type)
				.filePlace(place)
				.isPublic(false)
				.user(user)
				.build();
		operationService.saveFile(file0);
		file.transferTo(new File(place+"/"+fileName));
		return "uploaded";
	}
	
	@PostMapping("/upload/place")
	public String uploadplace(@RequestParam("file")MultipartFile file) throws IllegalStateException, IOException {
		String type = file.getContentType();
		String fileName = file.getOriginalFilename();
		String place = operationService.getSuitablePlace(type,fileName);
		return place+"/"+fileName;
	}
	
	@RequestMapping("/download")
	public void downloadFile(@RequestParam("filename")String fileName , HttpServletResponse response) throws IOException {
		LocalFile lfile = operationService.getFileByName(fileName);
		operationService.downloader(lfile, response);
	}
	
	@RequestMapping("/share")
	public void share(@RequestParam("filename")String filename,
			@RequestParam("username")String username) {
		operationService.share(filename,username);
	}
	
	@RequestMapping("/publish")
	public void share(@RequestParam("filename")String filename) {
		operationService.publish(filename);
	}
	
	//files that user uploaded
	@GetMapping("/private")
	public List<FileModel> getAllFilesNames(){
		return operationService.getUserprivateFiles();
	}
	//files that user published
	@GetMapping("/published")
	public List<FileModel> getPublishedFiles(){
		return operationService.getUserPublishedFiles();
	}
	//files that user sent
	@GetMapping("/sent")
	public List<FileModel> getSentFiles(){
		return operationService.getUserSentFiles();
	}
	//files other users sent
	@GetMapping("/recived")
	public List<FileModel> getRecivedFiles(){
		return operationService.getUserRecivedFiles();
	}
}