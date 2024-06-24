package br.com.alura.Forum_Hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackages = "br.com.alura.Forum_Hub")
public class ForumHubApplication {

//	documentation: http://localhost:8080/swagger-ui.html

	public static void main(String[] args) {
		SpringApplication.run(ForumHubApplication.class, args);
		System.out.println("http://localhost:8080/swagger-ui.html");
	}

}
