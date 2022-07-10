package com.db.tahawy.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name =  "tbl_file")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalFile {
	@Id
	private String fileName;
	@OneToOne
	private User user;
	private String filePlace;
	private String fileType;
	
	public String getPath() {
		return filePlace+"/"+fileName;
	}
}
