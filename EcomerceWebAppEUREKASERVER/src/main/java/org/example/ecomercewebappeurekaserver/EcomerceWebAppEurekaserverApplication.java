package org.example.ecomercewebappeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class EcomerceWebAppEurekaserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomerceWebAppEurekaserverApplication.class, args);
	}

}
