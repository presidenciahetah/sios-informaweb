package com.sios.springcloud.msvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class MsvcServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcServicesApplication.class, args);
	}

}
