package com.db.tahawy.dao;

import java.util.List;

import com.db.tahawy.model.LocalFile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileJpa extends JpaRepository<LocalFile, String>{
	List<LocalFile> findByFileType(String fileType);
}
