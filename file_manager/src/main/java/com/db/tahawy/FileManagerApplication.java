package com.db.tahawy;


import com.db.tahawy.diractory.interfaces.IDir;
import com.db.tahawy.diractory.models.Dir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FileManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileManagerApplication.class, args);
		
		IDir father =  new Dir(new Dir("mahmoud"), "myDB");

		IDir dir = new Dir(father, "tahawyDB");
		
		dir.getUncles().stream().forEach(son ->{
			System.out.println(son.getName());
		});
	}

}
