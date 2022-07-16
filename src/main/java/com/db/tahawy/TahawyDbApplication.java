package com.db.tahawy;


import java.util.Optional;

import com.db.tahawy.dao.UserJpa;
import com.db.tahawy.model.User;
import com.db.tahawy.services.PrefrencesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class TahawyDbApplication {
	private final UserJpa userJpa;
	private final PrefrencesService prefrencesService;
	private final PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(TahawyDbApplication.class, args);
	}
	
	@Bean
	CommandLineRunner run() {
		return args -> {
			Optional<User> user = userJpa.findById("root");
			if(user.isEmpty()) {
				prefrencesService.setRoot(System.getProperty("user.home")
						+"/TemperoryLocation", passwordEncoder.encode("0000"));
			}
		};
	}
}
