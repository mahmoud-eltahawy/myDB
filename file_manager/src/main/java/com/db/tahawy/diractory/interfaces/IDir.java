package com.db.tahawy.diractory.interfaces;

import java.util.List;

public interface IDir extends IFile{

	void setSons();

	IDir addDir(String dirName);

	IFile addFile(String fileName);

	List<IDir> addDirs(List<String> dirNames);

	List<IFile> addFiles(List<String> fileNames);

	List<IFile> getSons();

}
