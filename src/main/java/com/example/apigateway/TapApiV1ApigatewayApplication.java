package com.example.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TapApiV1ApigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(TapApiV1ApigatewayApplication.class, args);
	}

}
