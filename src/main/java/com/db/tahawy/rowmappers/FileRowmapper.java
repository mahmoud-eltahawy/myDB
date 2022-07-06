
package com.db.tahawy.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.db.tahawy.model.LocalFile;

import org.springframework.jdbc.core.RowMapper;

public class FileRowmapper implements RowMapper<LocalFile>{

	@Override
	public LocalFile mapRow(ResultSet rs, int rowNum) throws SQLException {	
		return new LocalFile(rs.getString("owner_name"),
				rs.getString("file_name"),
				rs.getString("file_place"),
				rs.getString("file_type"));
	}
}