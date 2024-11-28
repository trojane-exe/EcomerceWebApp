package org.example.ecomercewebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EcomerceWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomerceWebAppApplication.class, args);
	}

}
