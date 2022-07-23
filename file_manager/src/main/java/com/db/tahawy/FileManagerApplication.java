package com.db.tahawy;



import com.db.tahawy.diractory.interfaces.IDir;
import com.db.tahawy.diractory.models.Dir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FileManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileManagerApplication.class, args);
		
		IDir dir = IDir.getUserHome();
		IDir dr = new Dir(dir, "Downloads");
		dr.existingSonsGetter().stream().forEach(son ->{
			System.out.println(son.getName());
		});
	}

}
