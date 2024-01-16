package raf.gymuserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class GymUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymUserServiceApplication.class, args);
	}

}
