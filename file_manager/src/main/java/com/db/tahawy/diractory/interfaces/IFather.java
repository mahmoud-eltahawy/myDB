package com.db.tahawy.diractory.interfaces;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface IFather {
	
	static List<File> getSubdirs(File file) {
		List<File> subdirs = new ArrayList<>();
		if(file.listFiles() != null) {
			subdirs = Arrays.asList(file.listFiles());
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

	String getAbsolutePath();

	Boolean getIsDiractory();

	void setIsDiractory(Boolean isDiractory);

	Double getSizeInGigaByte();

}
