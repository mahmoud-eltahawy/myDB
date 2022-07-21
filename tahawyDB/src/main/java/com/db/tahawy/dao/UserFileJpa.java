package com.db.tahawy.dao;

import com.db.tahawy.model.UserFile;
import com.db.tahawy.model.UserFileId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFileJpa extends JpaRepository<UserFile, UserFileId>{
}
