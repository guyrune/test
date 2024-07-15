package com.kakaobank.notification.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectMapperUtil {
	private static final ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	@SneakyThrows
	public static <T> String toJsonString(T object) {
		return objectMapper.writeValueAsString(object);
	}
}
