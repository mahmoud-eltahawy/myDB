package com.db.tahawy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalFile {
	private String owenerUserName;
	private String fileName;
	private String filePlace;
	private String fileType;
	
	public String getPath() {
		return filePlace+"/"+fileName;
	}
}
