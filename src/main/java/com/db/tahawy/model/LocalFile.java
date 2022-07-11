package com.db.tahawy.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name =  "files")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocalFile {
	@Id
	@Column(name = "name",length = 100,nullable = false)
	private String fileName;
	@OneToOne
	@JoinColumn(
			name = "user_name_key",
			referencedColumnName = "name",
			nullable = false
			)
	private User user;
	@Column(name = "place",length = 300,nullable = false)
	private String filePlace;
	@Column(name = "type",length = 50,nullable = false)
	private String fileType;
	@Column(name = "is_public",nullable = false,
			columnDefinition = "Boolean default false")
	private Boolean isPublic;
	
	public String getPath() {
		return filePlace+"/"+fileName;
	}
}
