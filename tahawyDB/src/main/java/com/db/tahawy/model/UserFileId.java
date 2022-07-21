package com.db.tahawy.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class UserFileId implements Serializable{
	@OneToOne()
	@JoinColumn(
			name = "user_name",
			referencedColumnName = "name"
			)
	private User user;
	@OneToOne
	@JoinColumn(
			name = "file_name",
			referencedColumnName = "name"
			)
	private LocalFile file;
}
