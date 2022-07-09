
package com.db.tahawy.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.db.tahawy.model.LocalFile;
import com.db.tahawy.model.User;
import com.db.tahawy.services.OperationsService;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class Operations {

	@Autowired
	private OperationsService operationService;
	
	@PostMapping("upload")
	public String uploadFile(@RequestParam("file")MultipartFile file) throws IllegalStateException, IOException {
		String type = file.getContentType();
		String fileName = file.getOriginalFilename();
		String place = operationService.getSuitablePlace(type,fileName);
		file.transferTo(new File(place+"/"+fileName));
		return "uploaded";
	}
	
	@PostMapping("uploadplace")
	public String uploadplace(@RequestParam("file")MultipartFile file) throws IllegalStateException, IOException {
		String type = file.getContentType();
		String fileName = file.getOriginalFilename();
		String place = operationService.getSuitablePlaceOnly(type,fileName);
		return place+"/"+fileName;
	}
	
	@GetMapping("allfiles")
	public List<LocalFile> getAllFilesNames(){
		return operationService.getUserAllFiles();
	}
	@GetMapping("filesbytype")
	public List<LocalFile> getAllFilesByType(@RequestParam("type")String type){
		return operationService.getUserAllFilesByType(type);
	}
	
	@RequestMapping("/download")
	public void downloadFile(@RequestParam("filename")String fileName , HttpServletResponse response) throws IOException {
		LocalFile lfile = operationService.getFileByName(fileName);
		operationService.downloader(lfile, response);
	}
	
	@RequestMapping("/lu")
	public List<User> listUSers(){
		return operationService.getUsers();
	}
}