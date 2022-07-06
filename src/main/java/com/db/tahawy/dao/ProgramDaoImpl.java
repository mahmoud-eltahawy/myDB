

package com.db.tahawy.dao;

import java.util.List;

import com.db.tahawy.model.LocalFile;
import com.db.tahawy.model.User;
import com.db.tahawy.model.UserStatic;
import com.db.tahawy.rowmappers.FileRowmapper;
import com.db.tahawy.rowmappers.UserRowmapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProgramDaoImpl implements ProgramDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public void setProgramRoot(String home) {
		String sql = "insert into users(user_name,password,homedir) values(?,?,?)";
		jdbcTemplate.update(sql , "root","0000",home);
	}

	@Override
	public void saveUser(User user) {
		String sql = "insert into users(user_name,password,homedir) values(?,?,?)";
		jdbcTemplate.update(sql , user.getUserName(),user.getPassword(),user.getHome());
	}

	@Override
	public User getUserByName(String userName) {
		String sql = "select * from users where user_name = ?";
		return jdbcTemplate.queryForObject(sql,new UserRowmapper(), userName);
	}

	@Override
	public List<User> listUsers() {
		String sql = "select * from users";
		return jdbcTemplate.query(sql,new UserRowmapper());
	}
	
	@Override
	public void saveFile(LocalFile localFile) {
		String sql = "insert into files(file_name,owner_name,file_place,file_type) values(?,?,?,?)";
		jdbcTemplate.update(sql , localFile.getFileName(),localFile.getOwenerUserName(),localFile.getFilePlace(),localFile.getFileType());
	}

	@Override
	public List<LocalFile> getUserAllFiles() {
		String sql = "select * from files where owner_name = ?";
		return jdbcTemplate.query(sql,new FileRowmapper(), UserStatic.getUserName());
	}

	@Override
	public List<LocalFile> getUserAllFilesByType(String type) {
		String sql = "select * from files where file_type like ? and owner_name = ?";
		type = "%"+type+"%";
		return jdbcTemplate.query(sql,new FileRowmapper(), type, UserStatic.getUserName());
	}

	@Override
	public LocalFile getFileByName(String name) {
		String sql = "select * from files where file_name=?";
		return jdbcTemplate.queryForObject(sql,new FileRowmapper(), name);
	}
}