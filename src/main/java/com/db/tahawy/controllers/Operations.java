package com.db.tahawy.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.db.tahawy.model.FileModel;
import com.db.tahawy.model.LocalFile;
import com.db.tahawy.model.User;
import com.db.tahawy.model.UserModel;
import com.db.tahawy.model.UserStatic;
import com.db.tahawy.services.OperationsService;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class Operations {
	
	private final OperationsService operationService;
	
	@PostMapping("upload")
	public String uploadFile(@RequestParam("file")MultipartFile file) throws IllegalStateException, IOException {
		String type = file.getContentType();
		String fileName = file.getOriginalFilename();
		String place = operationService.getSuitablePlace(type,fileName);
		operationService.saveFile(LocalFile.builder()
				.fileName(fileName)
				.fileType(type)
				.filePlace(place)
				.isPublic(false)
				.user(UserStatic.getUser())
				.build());
		file.transferTo(new File(place+"/"+fileName));
		return "uploaded";
	}
	
	@PostMapping("uploadplace")
	public String uploadplace(@RequestParam("file")MultipartFile file) throws IllegalStateException, IOException {
		String type = file.getContentType();
		String fileName = file.getOriginalFilename();
		String place = operationService.getSuitablePlace(type,fileName);
		return place+"/"+fileName;
	}
	
	//files that user uploaded
	@GetMapping("privatefiles")
	public List<FileModel> getAllFilesNames(){
		return operationService.getUserprivateFiles();
	}
	//files that user published
	@GetMapping("publishedfiles")
	public List<FileModel> getPublishedFiles(){
		return operationService.getUserPublishedFiles();
	}
	
	//files that other users published
	@GetMapping("publicfiles")
	public List<FileModel> getPublicFiles(){
		return operationService.getUserpublicFiles();
	}
	//files that user sent
	@GetMapping("sentfiles")
	public List<FileModel> getSentFiles(){
		return operationService.getUserSentFiles();
	}
	//files other users sent
	@GetMapping("recivedfiles")
	public List<FileModel> getRecivedFiles(){
		return operationService.getUserRecivedFiles();
	}
	@GetMapping("filesbytype")
	public List<FileModel> getAllFilesByType(@RequestParam("type")String type){
		return operationService.getAllFilesByType(type);
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
	
	@RequestMapping("/lu")
	public List<UserModel> listUSers(){
		return operationService.getUsers()
				.stream().map(p -> new UserModel(p.getUserName()))
				.collect(Collectors.toList());
	}
	
	@RequestMapping("/lt")
	public List<String> listTypes(){
		return operationService.getAllTypes();
	}
}