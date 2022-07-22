package com.db.tahawy.diractory.models;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.db.tahawy.diractory.interfaces.IDir;

public class Dir implements IDir{
	protected IDir father;
	protected String name;
	protected List<IDir> sons;
	private List<IDir> uncles;
	
	public Dir(IDir father ,String name) {
		this.father = father;
		this.name = name;
	}

	public Dir(String name) {
		this.name = name;
	}

	@Override
	public String getAbsolutePath() {
		if(father != null) {
			return father.getAbsolutePath()+ "/" + name;
		} else {
			return "/" + name;
		}
	}
	
	@Override
	public String getFatherPath() {
		if(father != null) {
			return father.getAbsolutePath();
		} else {
			return "";
		}
	}
	
	@Override
	public String getGrandFatherPath() {
		if(father.getFather() != null) {
			return father.getFather().getAbsolutePath();
		} else {
			return "/";
		}
	}
	
	@Override
	public IDir getThisDir() {
		return new Dir(this.father,this.name);
	}
	
	@Override
	public IDir getFather() {
		return this.father;
	}
	
	@Override
	public IDir getGrandFather() {
		return this.father.getFather();
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public List<IDir> getSons(){
		return this.sons;
	}
	
	@Override
	public void setUnexistingSons(List<String> sons) {
		this.sons = sons.stream().map(func)
				.collect(Collectors.toList());
	}
		
	private Function<String, IDir> func = son -> {
				return new Dir(getThisDir(), son);
		};
	
	@Override
	public void setExistingSons() {
		if(this.sons==null) {
			File file = new File(getAbsolutePath());
			if(!file.exists()) {
					file.mkdir();
			}
			
			String[] sonsNames = file.list();
			if(sonsNames!=null) {
			this.sons = Arrays.stream(sonsNames).map(func)
					.collect(Collectors.toList());
			}
		}
	}
	
	@Override
	public List<IDir> existingSonsGetter(){
		setExistingSons();
		return sons;
	}
	
	@Override
	public IDir sonsSetter(List<String> sons) {
		IDir dir = getThisDir();
		dir.setUnexistingSons(sons);
		return dir;
	}
	
	@Override
	public void delete() {
		new File(getAbsolutePath()).delete();
	}
	
	@Override
	public void touch(){
		new File(getAbsolutePath()).mkdir();
	}
	
	@Override
	public Double getSizeInGigaByte() {
		if(new File(getAbsolutePath()).isDirectory()) {
			
			List<Double> sizes = IDir.getSubdirs(new File(getAbsolutePath()))
				.stream().map(son -> {
				if(son.isDirectory()) {
					return 0d;
				} else {
					return IDir.getFileSize
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
			return IDir.getFileSize(new File(getAbsolutePath()));
		}
	}
	
	@Override
	public IDir addDir(String dirName) {
		setExistingSons();
		IDir dir = new Dir(getThisDir(), dirName);
		dir.touch();
		sons.add(dir);
		return dir;
	}

	@Override
	public List<IDir> addDirs(List<String> dirNames) {
		setExistingSons();
		List<IDir> dirs = new ArrayList<>();
		dirNames.stream().forEach(dirName -> {
			IDir dir = new Dir(getThisDir(), dirName);
			dir.touch();
			sons.add(dir);
			dirs.add(dir);
		});
		return dirs;
	}
	
	@Override
	public List<IDir> getUncles(){
		setUncles();
		return uncles;
	}

	@Override
	public void setUncles() {
		String[] unclesNames = new File(getFatherPath()).list();
		if(unclesNames != null) {
			uncles = Arrays.stream(unclesNames).map(uncle -> {
				return new Dir(new Dir(father,
						father.getFatherPath()),uncle);
			}).collect(Collectors.toList());
		} else {
			uncles = new ArrayList<>();
		}
	}
}
