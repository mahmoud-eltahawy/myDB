package com.db.tahawy.security;

import java.util.Set;

import static com.db.tahawy.security.AppUserPermission.*;

import com.google.common.collect.Sets;

public enum AppUserRole {
	USER(Sets.newHashSet()),
	ADMIN(Sets.newHashSet(FILE_READ,FILE_WRITE,FILE_PATH,FILE_DELETE)),
	SUPER_USER(Sets.newHashSet(FILE_READ,FILE_WRITE,FILE_PATH,FILE_DELETE));
	
	private final Set<AppUserPermission> permissions;

	AppUserRole(Set<AppUserPermission> permissions) {
		this.permissions = permissions;
	}
	
	public Set<AppUserPermission> getPermissions(){
		return this.permissions;
	}
}
