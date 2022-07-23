package com.db.tahawy.diractory.models;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.db.tahawy.diractory.interfaces.IDir;

public class Dir implements IDir{
	private IDir father;
	private String name;
	private Stack<String> absolutePath;
	private Set<IDir> sons = new HashSet<>();
	private Set<IDir> uncles = new HashSet<>();
	
	public Dir(IDir father ,String name) {
		this.father = father;
		this.name = name;
	}
	
	public Dir(String name) {
		this.name = name;
	}

	@Override
	public Stack<String> getAbsolutePath() {
		setAbsolutePath();
		return absolutePath;
	}
	
	@Override
	public String getPath() {
		setAbsolutePath();
		if(absolutePath.isEmpty()) {
			return "/";
		}
		String path = "";
		while(!absolutePath.isEmpty()) {
			path =path+"/"+absolutePath.pop();
		}
		return path;
	}
	
	@Override
	public void setAbsolutePath() {
		this.absolutePath = IDir.fathersNames(getThisDir(),
				new Stack<>());
	}
	
	@Override
	public String getFatherPath() {
		if(father != null) {
			return father.getPath();
		} else {
			return "";
		}
	}
	
	@Override
	public String getGrandFatherPath() {
		if(father.getFather() != null) {
			return father.getFather().getPath();
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
		if(this.father == null) {
			return IDir.getRoot();
		} else {
			return this.father;
		}
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
	public Set<IDir> getSons(){
		return this.sons;
	}
	
	@Override
	public void setUnexistingSons(Set<String> sons) {
		this.sons = sons.stream().map(func)
				.collect(Collectors.toSet());
	}
		
	private Function<String, IDir> func = son -> {
				return new Dir(getThisDir(), son);
		};
	
	@Override
	public void setExistingSons() {
		if(this.sons==null) {
			String[] sonsNames = new File(getPath()).list();
			if(sonsNames != null) {
				this.sons = Arrays.stream(sonsNames).map(func)
						.collect(Collectors.toSet());
			}
		}
	}
	
	@Override
	public Set<IDir> existingSonsGetter(){
		setExistingSons();
		return sons;
	}
	
	@Override
	public IDir getDirExtraSons(Set<String> sons) {
		IDir dir = getThisDir();
		dir.setUnexistingSons(sons);
		return dir;
	}
	
	@Override
	public Set<IDir> getUncles(){
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
			}).collect(Collectors.toSet());
		} else {
			uncles = new HashSet<>();
		}
	}
	
	@Override
	public void delete() {
		new File(getPath()).delete();
	}
	
	@Override
	public void touch(){
		new File(getPath()).mkdir();
	}
	
	@Override
	public Double getSizeInGigaByte() {
		if(new File(getPath()).isDirectory()) {
			
			List<Double> sizes = IDir.getSubdirs(new File(getPath()))
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
			return IDir.getFileSize(new File(getPath()));
		}
	}

	@Override
	public IDir gotoExistingSon(String son) {
		setExistingSons();
		if(sons.contains(new Dir(getThisDir(), son))) {
			return new Dir(getThisDir(), son);
		} else {
			return getThisDir();
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
	public Set<IDir> addDirs(Set<String> dirNames) {
		setExistingSons();
		Set<IDir> dirs = new HashSet<>();
		dirNames.stream().forEach(dirName -> {
			IDir dir = new Dir(getThisDir(), dirName);
			dir.touch();
			sons.add(dir);
			dirs.add(dir);
		});
		return dirs;
	}
}
