package com.db.tahawy.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.db.tahawy.model.User;

import org.springframework.jdbc.core.RowMapper;

public class UserRowmapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {	
		return new User(rs.getString("user_name"),
				rs.getString("password"),
				rs.getString("homedir"));
	}

}
