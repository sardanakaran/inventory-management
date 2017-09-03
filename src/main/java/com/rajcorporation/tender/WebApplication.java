package com.rajcorporation.tender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.validation.Validator;

import com.rajcorporation.tender.repository.BOQItemRepository;
import com.rajcorporation.tender.repository.BOQRepository;
import com.rajcorporation.tender.repository.StorageProperties;
import com.rajcorporation.tender.repository.TenderRepository;
import com.rajcorporation.tender.validator.BOQCompositeValidator;
import com.rajcorporation.tender.validator.BOQItemValidator;
import com.rajcorporation.tender.validator.BOQValidator;

@SpringBootApplication
@Import(value = SwaggerConfiguration.class)
@EnableConfigurationProperties(StorageProperties.class)
public class WebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(WebApplication.class, args);
	}

	@Bean
	public BOQCompositeValidator validator(TenderRepository tenderRepo, BOQRepository boqRepo,
			BOQItemRepository itemRepo) {
		BOQValidator boqValidtor = new BOQValidator(tenderRepo, boqRepo);
		BOQItemValidator itemValidator = new BOQItemValidator(boqRepo, tenderRepo);

		return new BOQCompositeValidator(new Validator[] { boqValidtor, itemValidator });

	}
}
