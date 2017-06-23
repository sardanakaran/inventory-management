/**
 * 
 */
package com.rajcorporation.tender;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author karan
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	@Bean
	public Docket api() {
		AuthorizationScope[] authScopes = new AuthorizationScope[1];
		authScopes[0] = new AuthorizationScopeBuilder().scope("read").description("Read Access").build();

		SecurityReference securityReference = SecurityReference.builder().reference("test").scopes(authScopes).build();

		ArrayList<SecurityReference> reference = new ArrayList<SecurityReference>(1);
		reference.add(securityReference);

		ArrayList<SecurityContext> securityContexts = new ArrayList<SecurityContext>(1);
		securityContexts.add(SecurityContext.builder().securityReferences(reference).build());

		ArrayList<SecurityScheme> auth = new ArrayList<SecurityScheme>(1);
		auth.add(new BasicAuth("test"));

		return new Docket(DocumentationType.SWAGGER_2).securitySchemes(Arrays.asList(new BasicAuth("test")))
				.securityContexts(securityContexts).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("API").version("1.0").build();
	}
}
