package com.kakaobank.notification.common.type;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum SendStatusType {
	READY,
	PROCESSING,
	SUCCESS,
	FAIL;

	public static SendStatusType getSendStatusType(String name) {
		return Arrays.stream(SendStatusType.values())
					 .filter(type -> type.name().equalsIgnoreCase(name))
					 .findFirst()
					 .orElseThrow(() -> new NoSuchElementException("No constant with text " + name + " found"));
	}
}
