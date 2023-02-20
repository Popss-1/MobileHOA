package com.bigbrain.v1;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


//CommandLineRunner is used to run some code once after the app starts
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableBatchProcessing
public class BigBrainApplication {
	

	
	public static void main(String[] args) {
		SpringApplication.run(BigBrainApplication.class, args);
	}


}
