package com.WCCSecurity.securitycourse;

import com.WCCSecurity.securitycourse.entity.Role;
import com.WCCSecurity.securitycourse.entity.User;
import com.WCCSecurity.securitycourse.repository.RoleRepository;
import com.WCCSecurity.securitycourse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;


@SpringBootApplication
public class SecurityCourseApplication{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(SecurityCourseApplication.class, args);
	}

	@Bean
	public CommandLineRunner run() throws Exception {
		return (String[] args) -> {
			userRepository.save(new User(1, "Tony Stark", "123456", "stark@gmail.com"));
			userRepository.save(new User(2, "Nick Fury", "123456", "fury@gmail.com"));
			roleRepository.save(new Role(1, "ADMIN"));
			roleRepository.save(new Role(2, "USER"));

			//attribution du rôle USER à Tony Stark
			Optional<User> userStark = userRepository.findByUsername("Tony Stark");
			Optional<Role> roleUser = roleRepository.findByRole("USER");

			if (userStark.isPresent() && roleUser.isPresent()){
				User stark = userStark.get();
				Role user = roleUser.get();
				// Vérifiez si l'utilisateur a déjà ce rôle pour éviter les doublons
				if (!stark.getRoles().contains(user)) {
					stark.getRoles().add(user); // Ajoutez le rôle à la liste des rôles de l'utilisateur
					userRepository.save(stark); // Sauvegardez l'utilisateur mis à jour
				}
			}

			// Attribution du rôle "ADMIN" à Nick Fury
			Optional<User> userFury = userRepository.findByUsername("Nick Fury");
			Optional<Role> roleAdmin = roleRepository.findByRole("ADMIN");

			if (userFury.isPresent() && roleAdmin.isPresent()){
				User fury = userFury.get();
				Role admin = roleAdmin.get();
				if (!fury.getRoles().contains(admin)) {
					fury.getRoles().add(admin);
					userRepository.save(fury);
				}
			}

		};
	}
}
