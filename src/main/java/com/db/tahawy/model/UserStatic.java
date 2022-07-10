package com.db.tahawy.model;

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
	
	public static User getUser() {
		return new User(userName, password, home,null);
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