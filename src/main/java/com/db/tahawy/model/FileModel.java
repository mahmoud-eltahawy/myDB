package com.db.tahawy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileModel {
	private String fileName;
	private UserModel usermodel;
	private String fileType;
	private Boolean isPublic;
}
