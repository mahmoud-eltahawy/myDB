package com.db.tahawy.security;

public enum AppUserPermission {
	FILE_READ("file:read"),
	FILE_PATH("file:path"),
	FILE_WRITE("file:write"),
	FILE_DELETE("file:delete");
	
	private String permission;

	AppUserPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}
}
