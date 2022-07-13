package com.db.tahawy.model;

import java.util.ArrayList;
import java.util.List;

public class UserStatic {
	private static String userName = null;
	private static String password = null;
	private static String home = null;
	
	public static void setUser(User user) {
		userName = user.getUserName();
		password =user.getPassword();
		home = user.getHome();
	}
	
	public static void freeUser() {
		userName =null;
		password =null;
		home = null;
	}
	
	public static FileModel modelFile(LocalFile file) {
		return FileModel.builder()
				.fileName(file.getFileName())
				.fileType(file.getFileType())
				.isPublic(file.getIsPublic())
				.usermodel(new UserModel(file.getUser().getUserName()))
				.build();
	}
	
	public static List<FileModel> modelFile(List<LocalFile> files) {
		List<FileModel> result = new ArrayList<>();
		for (LocalFile file : files) {
			result.add(modelFile(file));
		}
		return result;
	}
	
	public static UserModel modelUser(User user) {
		return new UserModel(user.getUserName());
	}
	
	public static List<UserModel> modelUser(List<User> users) {
		List<UserModel> result = new ArrayList<>();
		for (User user : users) {
			result.add(new UserModel(user.getUserName()));
		}
		return result;
	}
	
	public static User getUser() {
		return new User(userName, password, home);
	}
	
	public static String getUserName() {
		return userName;
	}
	public static void setUserName(String userName) {
		UserStatic.userName = userName;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		UserStatic.password = password;
	}
	public static String getHome() {
		return home;
	}
	public static void setHome(String home) {
		UserStatic.home = home;
	}
}