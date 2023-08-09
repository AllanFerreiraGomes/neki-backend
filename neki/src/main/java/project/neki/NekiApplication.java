package project.neki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"project.neki", "security"}) // Add other package names as needed
public class NekiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NekiApplication.class, args);
    }
}
	