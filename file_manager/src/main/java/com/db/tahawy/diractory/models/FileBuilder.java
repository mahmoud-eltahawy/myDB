package com.db.tahawy.diractory.models;


import com.db.tahawy.diractory.interfaces.IDir;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileBuilder {
	private Dir dir;
	
	
	public void build() {
		builder(dir);
	}
	
	private void builder(IDir son) {
		son.touch();
		son.getSons().stream().forEach(sn -> {
			builder(sn);
		});
	}
}
