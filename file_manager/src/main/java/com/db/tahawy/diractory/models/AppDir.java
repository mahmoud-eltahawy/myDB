package com.db.tahawy.diractory.models;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.db.tahawy.diractory.interfaces.IDir;
import com.db.tahawy.diractory.interfaces.IFather;
import com.db.tahawy.diractory.interfaces.IFile;

public class AppDir extends AppFile implements IDir{
	protected List<IFile> sons;
	
	public AppDir(IFather father, String name) {
		super(father, name);
		this.isDiractory = true;
	}
	
	@Override
	public List<IFile> getSons(){
		setSons();
		return sons;
	}
	
	@Override
	public void touch(){
		new File(getAbsolutePath()).mkdir();
	}
	
	@Override
	public void setSons() {
		if(this.sons==null) {
			File file = new File(getAbsolutePath());
			if(!file.exists()) {
				if(isDiractory) {
					file.mkdir();
				} else {
					try {
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			String[] sonsNames = file.list();
			if(sonsNames!=null) {
			this.sons = Arrays.stream(sonsNames).map(func)
					.collect(Collectors.toList());
			}
		}
	}
		
	private Function<String, IFile> func = son -> {
		File file = new File(getAbsolutePath()+"/"+ son);
			if(file.isDirectory()) {
				return new AppDir(new Father(getAbsolutePath()), son);
			}else {
				return new AppFile(new Father(getAbsolutePath()), son);
			}
		};
	
	@Override
	public IDir addDir(String dirName) {
		setSons();
		IDir dir = new AppDir(new Father(getAbsolutePath()), dirName);
		dir.touch();
		sons.add(dir);
		return dir;
	}

	@Override
	public List<IDir> addDirs(List<String> dirNames) {
		setSons();
		List<IDir> dirs = new ArrayList<>();
		dirNames.stream().forEach(dirName -> {
			IDir dir = new AppDir(new Father(getAbsolutePath()), dirName);
			dir.touch();
			sons.add(dir);
			dirs.add(dir);
		});
		return dirs;
	}
	
	@Override
	public IFile addFile(String fileName) {
		setSons();
		IFile file = new AppFile(new Father(getAbsolutePath()), fileName);
		file.touch();
		sons.add(file);
		return file;
	}
	
	@Override
	public List<IFile> addFiles(List<String> fileNames) {
		setSons();
		List<IFile> files = new ArrayList<>();
		fileNames.stream().forEach(fileName ->{
			IFile file = new AppFile(new Father(getAbsolutePath()), fileName);
			file.touch();
			sons.add(file);
			files.add(file);
		});
		return files;
	}
	
}
