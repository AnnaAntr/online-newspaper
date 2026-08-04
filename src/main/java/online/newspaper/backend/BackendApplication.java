package online.newspaper.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.logging.LogManager;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		try {
			LogManager.getLogManager().readConfiguration(
					BackendApplication.class.getResourceAsStream("/logging.properties")
			);
		} catch (IOException e) {
			System.out.println("Cant load config");
		}

        SpringApplication.run(BackendApplication.class, args);
	}

}
