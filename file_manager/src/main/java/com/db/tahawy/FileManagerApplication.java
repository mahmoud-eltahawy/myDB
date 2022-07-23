package com.db.tahawy;



import com.db.tahawy.diractory.interfaces.IDir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FileManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileManagerApplication.class, args);
		
		IDir dir = IDir.getUserHome().gotoExistingSon("Downloads");
		System.out.println(dir.getSizeInGigaByte());
	}

}
