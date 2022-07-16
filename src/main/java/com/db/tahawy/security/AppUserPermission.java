package com.db.tahawy.security;

public enum AppUserPermission {
	FILE_UPLOAD("file:upload"),
	FILE_PLACE("file:place"),
	FILE_DOWNLOAD("file:download"),
	FILE_SHARE("file:share"),
	USER_IN("user:in"),
	FILE_PUBLISH("file:publish"),
	FILE_DELETE("file:delete"),
	
	USER_LIST("user:list"),
	TYPE_LIST("type:list"),
	TYPE_FILES("types:files"),
	ROOT_SET("root:set");
	
	
	
	private String permission;

	AppUserPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}
}
