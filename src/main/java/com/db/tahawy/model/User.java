package com.db.tahawy.model;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name =  "tbl_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	private String userName;
	private String password;
	private String home;
	@OneToMany
	private List<LocalFile> files;
}
