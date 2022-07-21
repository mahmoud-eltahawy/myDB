package com.db.tahawy.diractory.interfaces;

public interface IFile extends IFather{

	IFather getFather();

	void touch();

	void delete();

	String getFatherPath();

	IDir castToDir();
}
