package com.kakaobank.notification.common.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
	public static final String BASE_PACKAGE = "com.kakaobank.notification.controller";

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/swagger-ui.html", "/swagger-ui/index.html");
	}

	@Bean
	public GroupedOpenApi apiInfo() {
		return GroupedOpenApi.builder().group("notification")
							 .packagesToScan(BASE_PACKAGE)
							 .build();
	}
}

