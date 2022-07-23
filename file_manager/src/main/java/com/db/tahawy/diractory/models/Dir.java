package com.db.tahawy.diractory.models;

import java.io.File;
import java.io.IOException;
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
	public Stack<String> getPathStack() {
		return IDir.stackAbsolutePath(getThisDir());
	}
	
	@Override
	public String getPath() {
		Stack<String> pathStack = IDir.stackAbsolutePath(getThisDir());
		if(pathStack.isEmpty()) {
			return "/";
		}
		String path = "";
		while(!pathStack.isEmpty()) {
			path =path+"/"+pathStack.pop();
		}
		return path;
	}
	
	@Override
	public String getFatherPath() {
		return father.getPath();
	}
	
	@Override
	public String getGrandFatherPath() {
		return father.getFather().getPath();
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
		return getFather().getFather();
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
	public Set<IDir> setUnexistingSons(Set<String> sons) {
		Set<IDir> usons = sons.stream().map(func)
				.collect(Collectors.toSet());
		this.sons.addAll(usons);
		return usons;
	}
		
	private Function<String, IDir> func = son -> {
				return new Dir(getThisDir(), son);
		};
	
	@Override
	public void setExistingSons() {
		if(this.sons.isEmpty()) {
			String[] sonsNames = new File(getPath()).list();
			if(sonsNames != null) {
				this.sons.addAll(Arrays.stream(sonsNames).map(func)
						.collect(Collectors.toSet()));
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
			uncles.addAll(Arrays.stream(unclesNames).map(uncle -> {
				return new Dir(new Dir(father,
						father.getFatherPath()),uncle);
			}).collect(Collectors.toSet()));
		}
	}
	
	@Override
	public void delete() {
		new File(getPath()).delete();
	}
	
	@Override
	public void mkdir(){
		new File(getPath()).mkdir();
	}
	
	@Override
	public File addFile(String fileName){
		File file = new File(getPath()+"/"+fileName);
		try {
			file.createNewFile();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
			return file;
		}
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

	
	public Set<String> existingSonsStrings(){
		String[] names = new File(getPath()).list();
		return Arrays.stream(names).collect(Collectors.toSet());
	}
	
	@Override
	public IDir gotoExistingSon(String son) {
		if(existingSonsStrings().contains(son)) {
			return new Dir(getThisDir(), son);
		} else {
			return getThisDir();
		}
	}
	
	@Override
	public IDir addDir(String dirName) {
		setExistingSons();
		IDir dir = new Dir(getThisDir(), dirName);
		dir.mkdir();
		sons.add(dir);
		return dir;
	}
	
	@Override
	public Set<IDir> addDirs(Set<String> dirNames) {
		setExistingSons();
		Set<IDir> dirs = new HashSet<>();
		dirNames.stream().forEach(dirName -> {
			IDir dir = new Dir(getThisDir(), dirName);
			dir.mkdir();
			sons.add(dir);
			dirs.add(dir);
		});
		return dirs;
	}
}
