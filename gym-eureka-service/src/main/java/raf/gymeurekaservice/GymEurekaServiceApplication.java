package raf.gymeurekaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class GymEurekaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymEurekaServiceApplication.class, args);
	}

}
