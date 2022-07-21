package com.db.tahawy.diractory.models;


import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import com.db.tahawy.diractory.interfaces.IFather;


public class Father implements IFather{
	protected String name;
	protected Boolean isDiractory = true;
	
	public Father(String name) {
		this.name = name;
	}

	@Override
	public String getAbsolutePath() {
		return name;
	}

	@Override
	public Boolean getIsDiractory() {
		return isDiractory;
	}

	@Override
	public void setIsDiractory(Boolean isDiractory) {
		this.isDiractory = isDiractory;
	}
	
	@Override
	public Double getSizeInGigaByte() {
		if(new File(getAbsolutePath()).isDirectory()) {
			
			List<Double> sizes = IFather.getSubdirs(new File(getAbsolutePath()))
				.stream().map(son -> {
				if(son.isDirectory()) {
					return 0d;
				} else {
					return IFather.getFileSize
							(new File(son.getAbsolutePath()));
				}
			}).collect(Collectors.toList());
			
			Double totalSize = 0d;
			if(sizes != null) {
				for (Double size : sizes) {
					totalSize = totalSize + size;
				}
			}
			
			return totalSize;
		}else {
			return IFather.getFileSize(new File(getAbsolutePath()));
		}
	}
}
