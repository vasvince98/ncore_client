package com.vasvince.ncore_client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NcoreClientApplication {

	private static final Logger logger = LoggerFactory.getLogger(NcoreClientApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(NcoreClientApplication.class, args);
		logger.info("Environment test2");
	}

}
