package com.salonViky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableAspectJAutoProxy

public class SalonBellezaVikyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalonBellezaVikyApplication.class, args);
	}

	
}
