package com.db.tahawy;

import com.db.tahawy.diractory.interfaces.IDir;
import com.db.tahawy.diractory.models.AppDir;
import com.db.tahawy.diractory.models.Father;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileManagerApplication.class, args);
		
		IDir dir = new AppDir(new Father("/home/mahmoud/Downloads")
				, "Spring Security _ FULL COURSE.mp4");
		System.out.println(dir.getSizeInGigaByte());
	}

}
