package com.db.tahawy.security;

import java.util.Set;

import static com.db.tahawy.security.AppUserPermission.*;

import com.google.common.collect.Sets;

public enum AppUserRole {
	USER(Sets.newHashSet(FILE_UPLOAD,FILE_PLACE,FILE_DOWNLOAD,
			FILE_PUBLISH,FILE_SHARE,FILE_DELETE,USER_IN)),
	SUPER_USER(Sets.newHashSet(FILE_UPLOAD,FILE_PLACE,FILE_DOWNLOAD,
			FILE_PUBLISH,FILE_SHARE,FILE_DELETE,USER_IN)),
	ADMIN(Sets.newHashSet(USER_LIST,TYPE_LIST,TYPE_FILES,ROOT_SET));
	
	private final Set<AppUserPermission> permissions;

	AppUserRole(Set<AppUserPermission> permissions) {
		this.permissions = permissions;
	}
	
	public Set<AppUserPermission> getPermissions(){
		return this.permissions;
	}
}
