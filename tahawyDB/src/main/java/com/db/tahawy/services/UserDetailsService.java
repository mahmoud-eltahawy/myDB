package com.db.tahawy.services;

import java.util.ArrayList;
import java.util.Collection;

import com.db.tahawy.dao.UserJpa;
import com.db.tahawy.model.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{
	private final UserJpa userJpa;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userJpa.findById(username).get();
		return org.springframework.security.core.userdetails.User.builder()
				.username(user.getUserName())
				.password(user.getPassword())
				.roles(user.getRole())
				.build();
	}
}
