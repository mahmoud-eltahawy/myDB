package com.db.tahawy.diractory.interfaces;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.db.tahawy.diractory.models.Dir;

public interface IDir {
	static IDir getRoot() {
		return new Dir(new Dir(""), "");
	}
	
	static IDir getRootDir(String dir){
		return new Dir(getRoot(), dir);
	}
	
	static IDir getHome(){
		return new Dir(getRoot(), "home");
	}
	
	static IDir getUserHome() {
		return new Dir(getHome(), System.getProperty("user.name"));
	}
	
	static Stack<String> fathersNames(IDir dir,Stack<String> names){
		names.add(dir.getName());
		if(!dir.getName().equals("")) {
			return fathersNames(dir.getFather(), names);
		} else {
			names.remove("");
			return names;
		}
	}
	
	static List<File> getSubdirs(File file) {
		List<File> subdirs = new ArrayList<>();
		File[] files = file.listFiles();
		if(files != null) {
			subdirs = Arrays.asList(files);
		}
	    subdirs = new ArrayList<File>(subdirs);

	    List<File> deepSubdirs = new ArrayList<File>();
	    for(File subdir : subdirs) {
	    	if(getSubdirs(subdir) != null) {
				deepSubdirs.addAll(getSubdirs(subdir)); 
	    	}
	    }
	    if(deepSubdirs != null) {
			subdirs.addAll(deepSubdirs);
	    }
	    return subdirs;
	}
	
	static Double getFileSize(File file) {
		try {
			if(!file.isDirectory()) {
			Double sizeInBytes= 0d;
			Double TO_GIGA_CONST = (double) (1024*1024*1024);
			FileInputStream fis = new FileInputStream(file);
			sizeInBytes = (double) fis.getChannel().size();
			fis.close();
			return sizeInBytes / TO_GIGA_CONST;
			}
			return 0d;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 0d;
		} catch (IOException e) {
			e.printStackTrace();
			return 0d;
		}
	}

	Double getSizeInGigaByte();

	void touch();

	void delete();

	IDir getFather();

	String getFatherPath();

	void setExistingSons();

	void setUnexistingSons(Set<String> sons);

	IDir addDir(String dirName);

	Set<IDir> addDirs(Set<String> dirNames);

	Set<IDir> getSons();

	Set<IDir> existingSonsGetter();

	IDir getDirExtraSons(Set<String> sons);

	Set<IDir> getUncles();

	void setUncles();

	IDir getThisDir();

	String getName();

	IDir getGrandFather();

	String getGrandFatherPath();

	IDir gotoExistingSon(String son);

	void setAbsolutePath();

	Stack<String> getAbsolutePath();

	String getPath();

}
