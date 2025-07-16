package com.base.recruitment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.base.recruitment.repository")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(JdbcConnectionDetails jdbc) {
		return args -> {
			String class_ = jdbc.getClass().getName();
			String url = jdbc.getJdbcUrl();
			String userName = jdbc.getUsername();
			String pass = jdbc.getPassword();
			String details = """
					class: %s
					URL: %s
					user: %s
					Pass: %s
					""";
			System.out.println(String.format(details, class_, url, userName, pass));
		};
	}
}
