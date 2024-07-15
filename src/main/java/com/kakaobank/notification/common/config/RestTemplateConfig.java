package com.kakaobank.notification.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
class RestTemplateConfig {

	private static final int CONNECTION_TIMEOUT_MS = (60 * 1000);
	private static final int READ_TIMEOUT_MS = (60 * 1000);

	@Bean
	public RestTemplate createRestTemplate() {
		var requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(CONNECTION_TIMEOUT_MS);
		requestFactory.setReadTimeout(READ_TIMEOUT_MS);

		return new RestTemplate(requestFactory);
	}
}
