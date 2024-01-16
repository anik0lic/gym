package raf.gymreservationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableEurekaClient
@SpringBootApplication
@EnableScheduling
public class GymReservationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymReservationServiceApplication.class, args);
	}

}
