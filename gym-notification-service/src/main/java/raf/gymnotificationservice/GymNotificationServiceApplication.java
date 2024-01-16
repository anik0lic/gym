package raf.gymnotificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class GymNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymNotificationServiceApplication.class, args);
	}

}
