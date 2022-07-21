package com.db.tahawy.diractory.models;

import java.io.File;
import java.io.IOException;

import com.db.tahawy.diractory.interfaces.IDir;
import com.db.tahawy.diractory.interfaces.IFather;
import com.db.tahawy.diractory.interfaces.IFile;


public class AppFile extends Father implements IFile{
	protected IFather father;
	
	
	public AppFile(IFather father,String name) {
		super(name);
		this.father = father;
		this.isDiractory = false;
	}
	
	@Override
	public IDir castToDir() {
		return new AppDir(this.father, this.name);
	}
	
	@Override
	public String getFatherPath() {
		return father.getAbsolutePath();
	}

	@Override
	public String getAbsolutePath() {
		return father.getAbsolutePath()+"/"+name;
	}
	
	@Override
	public IFather getFather() {
		return this.father;
	}
	
	@Override
	public void touch(){
		try {
			new File(getAbsolutePath()).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void delete() {
		new File(getAbsolutePath()).delete();
	}
}
