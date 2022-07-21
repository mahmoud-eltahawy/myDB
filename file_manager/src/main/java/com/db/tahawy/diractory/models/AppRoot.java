package com.db.tahawy.diractory.models;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.db.tahawy.diractory.interfaces.IFather;
import com.db.tahawy.diractory.interfaces.IFile;
import com.db.tahawy.diractory.interfaces.IRoot;


public class AppRoot extends AppDir implements IRoot {
	private List<IFile> uncles;
	
	public AppRoot(IFather father, String name) {
		super(father, name);
		this.isDiractory=true;
	}
	
	@Override
	public List<IFile> getUncles(){
		setUncles();
		return uncles;
	}

	@Override
	public void setUncles() {
		String[] unclesNames = new File(getFatherPath()).list();
		uncles = Arrays.stream(unclesNames).map(uncle -> {
			File file = new File(getFatherPath()+"/"+uncle);
			if(file.isDirectory()) {
				return new AppDir(new Father(getFatherPath()),uncle);
			}else {
				return new AppFile(new Father(getFatherPath()),uncle);
			}
		}).collect(Collectors.toList());
	}
}
