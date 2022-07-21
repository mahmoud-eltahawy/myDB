package com.db.tahawy.dao;

import com.db.tahawy.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpa extends JpaRepository<User, String>{
}
