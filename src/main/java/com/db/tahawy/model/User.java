package com.db.tahawy.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name =  "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@Column(name = "name",length = 100,nullable = false)
	private String userName;
	@Column(name = "password",length = 100,nullable = false)
	private String password;
	@Column(name = "home",length = 100,nullable = false)
	private String home;
}
