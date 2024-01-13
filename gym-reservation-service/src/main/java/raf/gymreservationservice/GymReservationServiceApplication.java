package raf.gymreservationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GymReservationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymReservationServiceApplication.class, args);
	}

}
