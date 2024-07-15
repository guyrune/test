package com.kakaobank.notification.common.type;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum NotificationType {
	SMS,
	EMAIL,
	KAKAOTALK;

	public static NotificationType getNotificationType(String name) {
		return Arrays.stream(NotificationType.values())
					 .filter(type -> type.name().equalsIgnoreCase(name))
					 .findFirst()
					 .orElseThrow(() -> new NoSuchElementException("No constant with text " + name + " found"));
	}

}
