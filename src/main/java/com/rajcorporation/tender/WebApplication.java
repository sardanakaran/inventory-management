package com.rajcorporation.tender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

import com.rajcorporation.tender.repository.StorageProperties;

@SpringBootApplication
@Import(value = SwaggerConfiguration.class)
@EnableConfigurationProperties(StorageProperties.class)
public class WebApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(WebApplication.class, args);
	}
}
