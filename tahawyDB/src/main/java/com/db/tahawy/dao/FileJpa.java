package com.db.tahawy.dao;

import java.util.List;

import com.db.tahawy.model.LocalFile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FileJpa extends JpaRepository<LocalFile, String>{
	List<LocalFile> findByFileTypeContaining(String fileType);
	List<LocalFile> findFileByIsPublic(boolean isPublic);
	@Query(
			value = "select * from files f where f.user_name_key = ?1 and is_public=true",
			nativeQuery = true
			)
	List<LocalFile> findPublishedFiles(String userName);
	@Query(
			value = "select f.* from files f where f.user_name_key = ?1"
					+ " and f.is_public = false and f.name not in "
					+ "( select uf.file_name from user_file uf where uf.user_name =?1)",
			nativeQuery = true
			)
	List<LocalFile> findUserPrivateFiles(String userName);
	@Query(
			value = "select f.* from files f where f.user_name_key <> ?1 and f.name in "
					+ "(select file_name from user_file where user_name = ?1)",
			nativeQuery = true
			)
	List<LocalFile> findUserRecivedFiles(String userName);
	@Query(
			value = "select f.* from files f where f.user_name_key = ?1 and f.name in "
					+ "(select file_name from user_file where user_name <> ?1)",
			nativeQuery = true
			)
	List<LocalFile> findUserSentFiles(String userName);
	@Query(
			value = "select distinct f.type from files f",
			nativeQuery = true
			)
	List<String> findAllTypes();
	@Query(
			value = "select f.is_public from files f where f.name = ?1",
			nativeQuery = true
			)
	boolean isFilePublic(String filename);
	@Modifying
	@Transactional
	@Query(
			value = "update files set is_public = false where name = ?1",
			nativeQuery = true
			)
	void depublish(String filename);
	@Modifying
	@Transactional
	@Query(
			value = "update files set is_public = true where name = ?1",
			nativeQuery = true
			)
	void publish(String filename);
}